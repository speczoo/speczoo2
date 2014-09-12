package com.graduation.controller;

import com.graduation.common.Pager;
import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.MydbElement;
import com.graduation.model.User;
import com.graduation.model.dto.Datadto;
import com.graduation.service.IMydbService;
import com.graduation.service.ISqlQueryService;
import com.graduation.util.ParameterHandleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/query")
public class QueryController{

    @Inject
    private IMydbService mydbService;

    @Inject
    private ISqlQueryService sqlQueryService;

    @RequestMapping("/query")
    public String toQuery(){
        return "query/query";
    }

    @RequestMapping("/getTableNames")
    @ResponseBody
    public List<String> tableNamesByClicked(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        List<MydbElement> mydbs =  this.mydbService.list(loginUser.getId());
        ArrayList<String> tableNames = new ArrayList<String>();
        for(MydbElement mydb : mydbs){
            tableNames.add(mydb.getTableName());
        }
        return tableNames;
    }


    @RequestMapping("/getFieldNames")
    @ResponseBody
    public List<String> fieldNamesByTableName(@RequestParam("tableName") String tableName){
        List<String> filedNames = new ArrayList<String>();
        try {
            filedNames =  this.mydbService.getFieldNamesOfTable(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filedNames;
    }

    @RequestMapping(value = "/getQueryResult",method = RequestMethod.POST)
    public String getQueryResult(@RequestParam("tableName") String tableName,HttpServletRequest request,Model model){
        List<String> error = new ArrayList<String>();
        String sql = ParameterHandleUtil.gernerateSql(tableName,request);
        System.out.println(sql);
        Pager<Map<String,Object>> pager = null;
        try {
            pager = this.sqlQueryService.executeSql(sql,error);
            if (null == pager)
                return "query/query";
        } catch (SQLException e) {
            // e.printStackTrace();
            error.add("SQL语句异常....,或者您删除的数据中含有被参照的数据");
            model.addAttribute("error",error);
            return "query/query";
        }
        List<Map<String,Object>> lmps = pager.getDatas();
        model.addAttribute("names", lmps.get(0).keySet());
        model.addAttribute("lmps", lmps);
        model.addAttribute("error", error);
        model.addAttribute("total",pager.getTotal());
        return "query/query";
    }
    
  /*  @AuthorityMethod(authorityTypes = AuthorityType.SQL_QUERY)
    @RequestMapping(value = "/getQueryResult",method = RequestMethod.POST)
    public Datadto getQueryResult1(@RequestParam("tableName") String tableName,HttpServletRequest request, Model model){
        List<String> error = new ArrayList<String>();
        String sql = ParameterHandleUtil.gernerateSql(tableName,request);
        System.out.println(sql);
        Datadto datadto = new Datadto();
        datadto.setTableName(tableName);
        Pager<Map<String,Object>> pager = null;
        try {
        	
            pager = this.sqlQueryService.executeSql(sql,error);
            if(null == pager.getDatas() || pager.getDatas().size() <=0 || null==pager)
            {
               // datadto.setMessage("没有数据.....");
                error.add("没有数据.....");
                datadto.setMessage(error);
                datadto.setResult(0);
                System.out.println("datadto.getNames():"+datadto.getNames());
                System.out.println("datadto.getNames()的size:"+datadto.getNames().size());
              //  return datadto;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            error.add("SQL语句异常....,或者您删除的数据中含有被参照的数据");
            datadto.setMessage(error);
            datadto.setResult(0);
            return datadto;
        }

        Set<String> names = pager.getDatas().get(0).keySet();
        datadto.setNames(names);
        datadto.setPager(pager);
        System.out.println("datadto.getTableName()"+datadto.getTableName());
        System.out.println("datadto.getPager().getDatas().get(0)::"+datadto.getPager().getDatas());
        System.out.println("datadto.getPager().getDatas()与SqlQueryController的lmps的值是一样的::"+datadto.getPager().getDatas());
        System.out.println("datadto.getNames()::"+datadto.getNames());
        if (tableName.equals("fithas")){
			model.addAttribute("haveFits", true);
    
        }else{
        	model.addAttribute("haveFits", false); 
        }
        model.addAttribute("total",datadto.getPager().getTotal());
        model.addAttribute("lmps", datadto.getPager().getDatas());
        model.addAttribute("names", datadto.getNames());
        model.addAttribute("error", error);
        return datadto;
       // return  "query/query";
    }
*/
}