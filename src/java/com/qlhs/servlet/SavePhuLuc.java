/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qlhs.servlet;

import com.qlhs.function.PhuLucBean;
import com.qlhs.function.TaoPhuLuc;
import com.qlhs.function.ThongTinXe;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jdom2.JDOMException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ntdung
 */
public class SavePhuLuc extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        ServletContext servletContext = getServletContext();
        String path = servletContext.getRealPath("/WEB-INF/");
        HttpSession session=request.getSession();  
        
        if(action.equals("download")){
            ArrayList<XWPFDocument> docList = new ArrayList<>();
            OutputStream out = response.getOutputStream();
            String madon = request.getParameter("madon");
            String bienso = request.getParameter("bienso");
            int loaixe = Integer.parseInt(request.getParameter("loaixe"));
            String typecar = request.getParameter("typecar");
            String ngaygio = request.getParameter("ngaygio");
            ngaygio = ngaygio.substring(ngaygio.indexOf("-")+1);
            String sokhung = request.getParameter("sokhung");
            String maonline = request.getParameter("maonline");
             String csgtname= request.getParameter("csgtname");
         //    System.out.println(csgtname);
             String csgtdc = request.getParameter("csgtdc");
             String footer = request.getParameter("footer");
             int loaidon = Integer.parseInt(request.getParameter("loaidon"));
             session.setAttribute("madon", madon);session.setAttribute("bienso", bienso);
             session.setAttribute("loaixe", loaixe);session.setAttribute("typecar", typecar);
             session.setAttribute("sokhung", sokhung);session.setAttribute("maonline", maonline);
             session.setAttribute("csgtname", csgtname);session.setAttribute("csgtdc", csgtdc);
             session.setAttribute("footer", footer);session.setAttribute("loaidon", loaidon);
             session.setAttribute("numberfile", madon.split(",").length);
            
            String[] ldon = {"","",""};
            String titledon = "";
            switch(loaidon){
                case 1: ldon[0] = "Thế chấp";ldon[1] = "Thế chấp";ldon[2] = "Đăng ký thế chấp";titledon = "THẾ CHẤP";
                    break;
                case 2: ldon[0] = "Thế chấp";ldon[1] = "Thế chấp";ldon[2] = "Đăng ký thay đổi";titledon = "THAY ĐỔI THẾ CHẤP";
                    break;
                case 3:ldon[0] = "Xóa thế chấp";ldon[1] = "Xóa thế chấp";ldon[2] = "Rút bớt tài sản";titledon = "THAY ĐỔI THẾ CHẤP";
                    break;
                case 4:ldon[0] = "Xóa thế chấp";ldon[1] = "Xóa thế chấp";ldon[2] = "Xóa đăng ký";titledon = "XÓA THẾ CHẤP";
                    break;
                default:
                    break;
            }
            String  pathImg = "demo";
            TaoPhuLuc tpl = new TaoPhuLuc();
            XWPFDocument doc = null;
            if(loaidon != 4){
                PhuLucBean plb = null;
                if(session.getAttribute("phuluc") != null){
                    plb = (PhuLucBean)session.getAttribute("phuluc");
                    if(!plb.getDonlandau().equals(maonline)){
                        plb = tpl.getThongTinXe(maonline);
                    }
                }else{
                    plb = tpl.getThongTinXe(maonline);
                }
                //PhuLucBean 
                String dontt = plb.getSodondangky();
                String bnbd = plb.getBnbd();
                String benbaodam = plb.getBenbaodam();
                String ngaydangky = plb.getThoigiandangky();
                String shortname = new com.qlhs.function.SavePhuLuc().tenLH(bnbd, path);
                int checkLength = sokhung.length();
                if(checkLength >0 ){
                    String[] sks = sokhung.split(",");
                    ArrayList<ThongTinXe> data = plb.getThongtinXe();
                    boolean check = false;
                    for(int i=0; i <  data.size();i++){
                        ThongTinXe dt = data.get(i);
                        for(String sokhungkt : sks){
                            if( !check){
                                if(dt.getSoKhung().toLowerCase().contains(sokhungkt.toLowerCase())){
                                    try {
                                        String biensoValue = dt.getBienSo() ;
                                        String sokhungValue =dt.getSoKhung();
                                        //  String sokhung  = plb.getThongtinXe().getSoKhung();
                                        String somay = dt.getSoMay();
                                        //   String somay = plb.getThongtinXe().getSoMay();
                                        String inforOther = plb.getTauca();
                                        String donlandau = plb.getDonlandau();
                                        // String inforOther = plb.getTauca();
                                        //    String pathImg = path+"/barcode/"+donid+".jpg";

                                        switch(loaixe){
                                            case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                                    shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                            break;
                                            case 2:doc = tpl.replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                                    shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                            break;
                                            case 3:doc = tpl.replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                                    shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                            break;
                                            default:
                                                break;
                                        }
                                     //   session.invalidate();
                                            session.setAttribute("phuluc", plb);
                                        check = true;

                                        //  docStyles[i] = docSub.getStyle();
                                        docList.add(doc);
                                    } catch (InvalidFormatException ex) {
                                        Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    }
//                    for(String sokhungkt : sks){
//                        
//                    //    System.out.println(data.size());
//                        for(int i=0; i <  data.size();i++){
//                            ThongTinXe dt = data.get(i);
////                            System.out.println("---------------------------------------------------------------");
////                            System.out.println(dt.getSoKhung());
////                            System.out.println(sokhungkt.toLowerCase());
////                            System.out.println("---------------------------------------------------------------");
//                            if(dt.getSoKhung().toLowerCase().contains(sokhungkt.toLowerCase())){
//                            //    System.out.println("---------------------------------11111------------------------------");
//                                try {
//                                    String biensoValue = dt.getBienSo() ;
//                                    String sokhungValue =dt.getSoKhung();
//                                    //  String sokhung  = plb.getThongtinXe().getSoKhung();
//                                    String somay = dt.getSoMay();
//                                    //   String somay = plb.getThongtinXe().getSoMay();
//                                    String inforOther = plb.getTauca();
//                                    String donlandau = plb.getDonlandau();
//                                    // String inforOther = plb.getTauca();
//                                    //    String pathImg = path+"/barcode/"+donid+".jpg";
//                                    
//                                    switch(loaixe){
//                                        case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        case 2:doc = tpl.replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        case 3:doc = tpl.replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        default:
//                                            break;
//                                    }
//                                    session.invalidate();
//                                    if(madon.split(",").length>reget){
//                                        session.setAttribute("phuluc", plb);
//                                    }
//                                    
//                                    //  docStyles[i] = docSub.getStyle();
//                                    docList.add(doc);
//                                } catch (InvalidFormatException ex) {
//                                    Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                              break;
//                            }else{
//                                try {
//                                    String inforOther = plb.getTauca();
//                                    String donlandau = plb.getDonlandau();
//                                    switch(loaixe){
//                                        case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        case 2:doc = tpl.replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        case 3:doc = tpl.replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
//                                                shortname, ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
//                                        break;
//                                        default:
//                                            break;
//                                    }
//                                } catch (InvalidFormatException ex) {
//                                    Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                 //           doc = mergerFIleWord(docSub);
//                             
//                        }
//                    }
                }else{
                    ArrayList<ThongTinXe> data = plb.getThongtinXe();
                    //    CTStyles[] docStyles = new CTStyles[data.size()];
                    for(int i=0; i <  data.size();i++){
                        try {
                            ThongTinXe dt = data.get(i);
                            String biensoValue = dt.getBienSo() ;
                            String sokhungValue =dt.getSoKhung();
                            String somay = dt.getSoMay();
                            String inforOther = plb.getTauca();
                            String donlandau = plb.getDonlandau();
                            // String inforOther = plb.getTauca();
                            //    String pathImg = path+"/barcode/"+donid+".jpg";
                            pathImg = "demo";
                          //  XWPFDocument doc = null;
                            switch(loaixe){
                                case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                break;
                                case 2:doc = tpl.replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                break;
                                case 3:doc = tpl.replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], typecar, sokhungValue, somay, biensoValue, csgtname, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                break;
                                default:
                                    break;
                            }
                            //docStyles[i] = docSub.getStyle();
                            docList.add(doc);
                        } catch (InvalidFormatException ex) {
                            Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                }
            }else{
                try {
                    pathImg = "demo";
                //    XWPFDocument doc = null;
                    String loailuu = request.getParameter("loailuu");
                    //System.out.println("LOAI LUU = "+loailuu);
                    String bnbd = "", benbaodam = "", ngaydangky ="";
                    if(loailuu.equals("1")){
                        // lấy mẫu
                        switch(loaixe){
                            case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx","", "", "", "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            case 2:doc = tpl.replaceText(path+"/data/romoc.docx","", "", "", "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            case 3:doc = tpl.replaceText(path+"/data/tauca.docx","", "", "", "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            default:
                                break;
                        }
                    }else if(loailuu.equals("2")){
                        // lấy từ dữ liệu
                        String ngaynhap = request.getParameter("ngaygio");
                        System.out.println(madon);System.out.println(ngaynhap);
                        ArrayList<String> inforDon =  tpl.getThongTinPL(madon, ngaynhap);
                        if(!inforDon.isEmpty()){
                            bnbd = inforDon.get(0);
                            benbaodam= inforDon.get(1);
                            ngaydangky = inforDon.get(2);
                        }
                        String bnbdValue = "";
                        for(int i=0; i < bnbd.split("\\|").length;i+=2){
                            if(i==0){
                                bnbdValue = bnbd.split("\\|")[i];
                            }else{
                                bnbdValue += " & "+bnbd.split("\\|")[i];
                            }
                        }
                        String bbdValue = "";
                        for(int i=0; i < benbaodam.split("\\|\\|").length;i+=2){
                            if(i==0){
                                bbdValue = benbaodam.split("\\|\\|")[i].split("\\|")[0];
                            }else{
                                bbdValue += " & "+benbaodam.split("\\|\\|")[i].split("\\|")[0];;
                            }
                        }
                        System.out.println(bnbd);System.out.println(benbaodam);System.out.println(ngaydangky);
                        switch(loaixe){
                            case 1: doc = tpl.replaceText(path+"/data/phuluc05.docx","", bnbdValue,bbdValue, "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            case 2:doc = tpl.replaceText(path+"/data/romoc.docx","", bnbd, benbaodam, "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            case 3:doc = tpl.replaceText(path+"/data/tauca.docx","", bnbd,benbaodam, "", "",
                                    "", ldon[0], ldon[1], ldon[2], typecar, "", "", "", csgtname, csgtdc,pathImg,"",madon,titledon,footer);
                            break;
                            default:
                                break;
                        }
                    }
                    
                    docList.add(doc);
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
             response.setHeader("Content-Disposition", "attachment; filename="+madon+".docx");
             doc.write(out);
             out.close();
        //    String[] ttdon = new ConnectDB().getMaonline(madon, ngaygio);
         //   XWPFDocument doc = new com.qlhs.function.SavePhuLuc().savePhuLuc05(path,maonline,sokhung,ttdon[0],loaixe,loaidon,typecar,bienso,madon,csgtname,csgtdc,footer);
//           XWPFDocument doc;
//            try {
//                doc = new com.qlhs.function.TaoPhuLuc().savePhuLuc05(path,maonline,sokhung,madon,loaixe,loaidon,typecar,bienso,madon,csgtname,csgtdc,footer,nxCheck,ngaygio);
//              //  System.out.println(doc.length);
//              //  response.setContentType("multipart/x-mixed-replace;boundary=END
//                 
//                  doc.write(out);
//                  out.close();
                
//            } catch (InvalidFormatException | OutputException | BarcodeException ex) {
//                Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("Word written FAILED..");
//                response.sendRedirect("404.jsp");
////                InputStream is = new URL("ASdasdasd").openStream();
////                byte[] buff = new byte[32*1024];
////                int nRead = 0;
////                while((nRead = is.read())!= -1){
////                    out.write(buff,0,nRead);
////                }
////                out.flush();
//            } catch (XmlException ex) {
//                Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if(doc != null){
//               
//            }else {
//                response.sendRedirect("404.jsp");
//            }
            
            
        }else if(action.equals("loadcsgt")){
            String csgtinfor = request.getParameter("csgt");
            System.out.println(csgtinfor);
             ArrayList<ArrayList<String>> csgt = null;
            try {
                csgt = new com.qlhs.function.SavePhuLuc().csgtInfor(csgtinfor, path);
            } catch (JDOMException ex) {
                Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONArray arr = new JSONArray();    
            
            if(!csgt.isEmpty() || csgt!= null){
                try {
                    for(ArrayList<String> csgtList : csgt){
                        JSONObject obj = new JSONObject();
                        obj.put("name", csgtList.get(0));
                        obj.put("diachi", csgtList.get(1));
                        arr.put(obj);
                    }
                    
                } catch (JSONException ex) {
                    Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
                }
                String data = arr.toString();
                //System.out.println(data);
                response.getWriter().write(data);
            }
            
        }else if(action.equals("gettsxe")){
            String maonline = request.getParameter("maonline");
            List<Object> total = new com.qlhs.function.SavePhuLuc().getTongSoXe(maonline);
            System.out.println("TD SIZE = "+total);
            JSONObject obj = new JSONObject();
            String sk = "";
            for(int i=1; i < total.size();i++){
                if(i==1){
                    sk += total.get(i).toString();
                }else{
                    sk += "_"+total.get(i).toString();
                }
            }
            try {
                obj.put("total", total.get(0));
                obj.put("sokhung", sk);
            } catch (JSONException ex) {
                Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
            }
            String data = obj.toString();
            response.getWriter().write(data);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
