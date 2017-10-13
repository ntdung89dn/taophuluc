/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qlhs.function;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import static com.qlhs.function.SavePhuLuc.rotate90ToLeft;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.jsoup.Jsoup;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

/**
 *
 * @author ntdung
 */
public class TaoPhuLuc {
    
         public XWPFDocument replaceText(String filedoc,String sodontt,String bnbd,String benbaodam,String ngaydangky,String donlandau,String shortname,
            String loaidon1,String loaidon2,String loaidon3,String Loaixe,String sokhung,String somay,String bienso,
            String csgt,String csgtdc,String imgFile,String otherInfor,String madon,String titledon,String footer) throws InvalidFormatException, IOException{
         Calendar cal = Calendar.getInstance();
         Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016 Calendar cal = Calendar.getInstance(); cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014 int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 6 int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17 int dayOfYear = cal.get(Calendar.DAY_OF_YEAR); //169 int month = cal.get(Calendar.MONTH); // 5 int year = cal.get(Calendar.YEAR); // 2016
         cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014 
         int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17
         int month = cal.get(Calendar.MONTH)+1; // 5
         int year = cal.get(Calendar.YEAR); // 2016

         if(sokhung == null || sokhung.trim().equals("")){
               sokhung =  otherInfor;
           }
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(filedoc));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("sodontt")) {
                        text = text.replace("sodontt", "");
                        r.setText(text, 0);
                    }else if(text != null && text.contains("tenphongcanhsat")){
                        text = text.replace("tenphongcanhsat", csgt);
                        r.setText(text, 0);
                    }else if(text != null && text.contains("diachicanhsat")){
                        text = text.replace("diachicanhsat", csgtdc);
                        r.setText(text, 0);
                    }else if(text != null && text.contains("xx1")){
                        text = text.replace("xx1", String.valueOf(dayOfMonth));
                        r.setText(text, 0);
                    }else if(text != null && text.contains("xx2")){
                        text = text.replace("xx2", String.valueOf(month));
                        r.setText(text, 0);
                    }else if(text != null && text.contains("2xxx")){
                        text = text.replace("2xxx", String.valueOf(year));
                        r.setText(text, 0);
                    }else if(text != null && text.contains("loaidon1")){
                        text = text.replace("loaidon1", loaidon1);
                        r.setText(text,0);
                  }else if(text != null && text.contains("TITLEDON")){
                        text = text.replace("TITLEDON", titledon);
                        r.setText(text,0);
                  }
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
           for (XWPFTableRow row : tbl.getRows()) {
              for (XWPFTableCell cell : row.getTableCells()) {
                 for (XWPFParagraph p : cell.getParagraphs()) {
                    for (XWPFRun r : p.getRuns()) {
                      String text = r.getText(0);
                      if (text.contains("benbd")) {
                        text = text.replace("benbd", benbaodam);
                        r.setText(text,0);
                      }else if( text.contains("madononline")){
                        text = text.replace("madononline", madon);
                        r.setText(text,0);
                       }else if(text.contains("anhBarcode")){
                           text = text.replace("anhBarcode", "");
                            r.setText(text,0);
                          //  String imgFile = "saved.jpg";
                          if(!imgFile.equals("demo")){
                              try (FileInputStream is = new FileInputStream(imgFile)) {
                                r.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(20),Units.toEMU(80));
                            }
                          }
                            
                       }else if( text.contains("SoKhung")){
                           
                        text = text.replace("SoKhung", sokhung);
                        r.setText(text,0);
                       }else if( text.contains("Somay")){
                        text = text.replace("Somay", somay);
                        r.setText(text,0);
                       }else if( text.contains("Bienso")){
                        text = text.replace("Bienso", bienso);
                        r.setText(text,0);
                       }else if( text.contains("Loaidon")){
                        text = text.replace("Loaidon", loaidon2);
                        r.setText(text,0);
                       }else if( text.contains("loaido")){
                        text = text.replace("loaido", loaidon3);
                        r.setText(text,0);
                       }else if( text.equals("tenkhachang")){
                        text = text.replace("tenkhachang", bnbd);
                        r.setText(text,0);
                       }else if( text.contains("thoigiandangky")){
                        text = text.replace("thoigiandangky", ngaydangky);
                        r.setText(text,0);
                       }else if( text.contains("sodondklandau")){
                        text = text.replace("sodondklandau", donlandau);
                        r.setText(text,0);
                       }else if( text.equals("tenkhachhang2")){
                        text = text.replace("tenkhachhang2", shortname);
                        r.setText(text,0);
                       }else if( text.contains("TypeofCar")){
                        text = text.replace("TypeofCar", Loaixe);
                        r.setText(text,0);
                       }else if( text.contains("infortauca")){
                        text = text.replace("infortauca", otherInfor);
                        r.setText(text,0);
                       }
                    }
                 }
              }
           }
        }
        //write footer content
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc, sectPr);
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        ctFooter.setStringValue(footer);	
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);
//        FileOutputStream fos = new FileOutputStream(sodontt+".docx");
//        doc.write(fos);
//        fos.close();    
        return doc;
    }
         
         
   // tạo file pl 05
      public XWPFDocument savePhuLuc05(String path,String maonline,String sk,String donid,int typecar,int loaidon,String loaixe,
           String biensocsgt,String madon,String csgtnameValue,String csgtdcValue,String footer,boolean nxe,String ngaylamdon) throws InvalidFormatException, IOException, OutputException, BarcodeException, XmlException{
             XWPFDocument doc =  null;
            String[] ldon = {"","",""};
            String titledon = "";
            switch(loaidon){
                case 1: ldon[0] = "Thế chấp";ldon[1] = "Thế chấp";ldon[2] = "Đăng ký lần đầu";titledon = "THẾ CHẤP";
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
            String csgt = csgtnameValue;
            String csgtdc = csgtdcValue;
            String  pathImg = "demo";
            if(loaidon != 4){
                PhuLucBean plb = getThongTinXe(maonline);
                String dontt = plb.getSodondangky();
                String bnbd = plb.getBnbd();
                String benbaodam = plb.getBenbaodam();
                String ngaydangky = plb.getThoigiandangky();
                String shortname = tenLH(bnbd);
                int checkLength = sk.length();
                if(checkLength >0 ){
                    String[] sks = sk.split(",");
                    ArrayList<ThongTinXe> data = plb.getThongtinXe();
                    for(String sokhungkt : sks){
                       // CTStyles[] docStyles = new CTStyles[sks.length];
                        for(int i=0; i <  data.size();i++){
                            ThongTinXe dt = data.get(i);
                            if(dt.getSoKhung().toLowerCase().contains(sokhungkt.toLowerCase())){
                                String bienso = dt.getBienSo() ;
                                String sokhung =dt.getSoKhung();
                              //  String sokhung  = plb.getThongtinXe().getSoKhung();
                                String somay = dt.getSoMay();
                             //   String somay = plb.getThongtinXe().getSoMay();
                                String inforOther = plb.getTauca();
                                String donlandau = plb.getDonlandau();
                               // String inforOther = plb.getTauca();
                            //    String pathImg = path+"/barcode/"+donid+".jpg";
                               
                               // XWPFDocument docSub = null;
                                switch(typecar){
                                    case 1: doc = replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    case 2:doc = replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    case 3:doc = replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    default:
                                        break;
                                }
                              //  docStyles[i] = docSub.getStyle();
                              
                            }
                 //           doc = mergerFIleWord(docSub);
                        }
                    }
                }else{
                    ArrayList<ThongTinXe> data = plb.getThongtinXe();
                    //    CTStyles[] docStyles = new CTStyles[data.size()];
                        for(int i=0; i <  data.size();i++){
                            ThongTinXe dt = data.get(i);
                            String bienso = dt.getBienSo() ;
                            String sokhung =dt.getSoKhung();
                            String somay = dt.getSoMay();
                            String inforOther = plb.getTauca();
                            String donlandau = plb.getDonlandau();
                               // String inforOther = plb.getTauca();
                            //    String pathImg = path+"/barcode/"+donid+".jpg";
                                pathImg = "demo";
                                switch(typecar){
                                    case 1: doc = replaceText(path+"/data/phuluc05.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    case 2:doc = replaceText(path+"/data/romoc.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    case 3:doc = replaceText(path+"/data/tauca.docx",dontt, bnbd, benbaodam, ngaydangky, donlandau,
                                        shortname, ldon[0], ldon[1], ldon[2], loaixe, sokhung, somay, bienso, csgt, csgtdc,pathImg,inforOther,madon,titledon,footer);
                                        break;
                                    default:
                                        break;
                                }
                                //docStyles[i] = docSub.getStyle();
                            }
                }
                
            }else{
                 pathImg = "demo";
                switch(typecar){
                    case 1: doc = replaceText(path+"/data/phuluc05.docx","", "", "", "", "",
                        "", ldon[0], ldon[1], ldon[2], loaixe, "", "", "", csgt, csgtdc,pathImg,"",madon,titledon,footer);
                        break;
                    case 2:doc = replaceText(path+"/data/romoc.docx","", "", "", "", "", 
                        "", ldon[0], ldon[1], ldon[2], loaixe, "", "", "", csgt, csgtdc,pathImg,"",madon,titledon,footer);
                        break;
                    case 3:doc = replaceText(path+"/data/tauca.docx","", "", "", "", "",
                        "", ldon[0], ldon[1], ldon[2], loaixe, "", "", "", csgt, csgtdc,pathImg,"",madon,titledon,footer);
                        break;
                    default:
                        break;
                }
            }
            
            
            return doc;
         }
         
     // lấy thông tin xe
         public PhuLucBean getThongTinXe(String maonline) throws IOException{
        PhuLucBean plb = new PhuLucBean();
           String url ="https://dktructuyen.moj.gov.vn/dtn_str/search/public/";
           String sodonlandau = "";
           String thoidiemdangky = "";
           String benbaodam = "";
           String bnbd = "";
           plb.setSodondangky(maonline);
          // ArrayList<ThongTinXe> ttxList = new ArrayList<>();
         try (final WebClient webClient = new WebClient()) {
             webClient.getOptions().setThrowExceptionOnScriptError(false);
                // Get the first page
                final HtmlPage page1 = webClient.getPage(url);
                // Get the form that we are dealing with and within that form, 
                // find the submit button and the field that we want to change.
                final HtmlForm form = page1.getHtmlElementById("search_registration_form");

                final HtmlButton button = form.getFirstByXPath("//form/button");
                final HtmlTextInput textField = form.getInputByName("registration_number");

                // Change the value of the text field
                textField.setValueAttribute(maonline);

                // Now submit the form by clicking the button and get back the second page.
                final HtmlPage page2 = button.click();
                
               List<HtmlDivision> divList = page2.getByXPath("//*[@id=\"contract_body\"]");
               
               for(HtmlDivision div : divList){
                   if(div.getTextContent().contains("Đăng ký lần đầu")){ 
                       HtmlTable firstTable = (HtmlTable)div.getFirstByXPath("//*[@id=\"registration_number_table\"]/table");
                       sodonlandau = firstTable.getCellAt(1, 0).getTextContent();
                    //   System.out.println(sodonlandau);
                       plb.setDonlandau(sodonlandau);
                   }
                   if(div.getTextContent().contains(maonline)){
                       org.jsoup.nodes.Document doc = Jsoup.parse(div.asXml());
                      // removeComments(doc);
                       org.jsoup.select.Elements TableElements = doc.getElementsByTag("table");
                       
                       for(int i=0; i< TableElements.size();i++){
                           if(i==0){
                               org.jsoup.select.Elements tds =  TableElements.get(i).getElementsByTag("td");
                               thoidiemdangky = tds.get(1).text();
                         //      System.out.println(thoidiemdangky);
                               plb.setThoigiandangky(thoidiemdangky);
                           }else if(i ==1){
                               org.jsoup.select.Elements tds =  TableElements.get(i).getElementsByTag("td");
                               for(int j=2;j < tds.size();j=j+4){
                                   tds.select("label").remove();
                                   if(benbaodam.equals("")){
                                       benbaodam += tds.get(j).text();
                                   }else{
                                       benbaodam += " & "+tds.get(j).text();
                                   }
                               }
                        //       System.out.println(benbaodam);
                               plb.setBenbaodam(benbaodam);
                           }else if(i==2){
                               org.jsoup.select.Elements tds =  TableElements.get(i).getElementsByTag("td");
                               for(int j=0;j < tds.size();j=j+2){
                                   if(bnbd.equals("")){
                                       bnbd += tds.get(j).text();
                                   }else{
                                       bnbd += " & "+tds.get(j).text();
                                   }
                               }
                            //   System.out.println(bnbd);
                               plb.setBnbd(bnbd);
                           }
                           if(TableElements.size() >3){
                           //    System.out.println("Số Khung - Số Máy - Biển Số");
                           ArrayList<ThongTinXe> data = new ArrayList<>();
                               if(i==3){
                                org.jsoup.select.Elements tds =  TableElements.get(i).getElementsByTag("td");
                                    for(int j=0;j < tds.size();j= j+3){
                                        ThongTinXe ttx = new ThongTinXe();
                                       String skS = "";
                                        if(tds.get(j) != null){
                                            skS = tds.get(j).text();
                                        }
                                        String smS = "";
                                        if(tds.get(j+1) != null){
                                            smS = tds.get(j+1).text();
                                        }
                                        String bsS = "";
                                        if(tds.get(j+2) != null){
                                            bsS = tds.get(j+2).text();
                                        }
                                        ttx.setSoKhung(skS);
                                        ttx.setSoMay(smS);
                                        ttx.setBienSo(bsS);
                                        data.add(ttx);
                                      //  System.out.println("Số khung : "+skS+" || Số máy : "+smS+" || Biển số: "+bsS);
                                    }
                                    
                                }
//                               for(ThongTinXe ttx : data){
//                                   System.out.println("Số khung : "+ttx.getSoKhung()+" || Số máy : "+ttx.getSoMay()+" || Biển số: "+ttx.getBienSo());
//                               }
                               plb.setThongtinXe(data);
                           }else if( i ==2){
                               org.jsoup.nodes.Element tds = doc.getElementById("description_assets");
                               plb.setTauca(tds.text());
                               ThongTinXe ttx = new ThongTinXe();
                               ArrayList<ThongTinXe> data = new ArrayList<>();
                               if(ttx.getSoKhung() == null){
                                   ttx.setSoKhung("");
                                    ttx.setSoMay("");
                                    ttx.setBienSo("");
                                    data.add(ttx);
                               }
                               plb.setThongtinXe(data); 
                                
                         //      System.out.println(tds.text());
                           }
                           
                       }
                   }
               }
           }
         return plb;
       }
         
         // Tên liên hệ
         public  String tenLH(String name){
            String s_name = "";
            if(name != null){
                name = name.replaceAll("\n", "");
                name = name.replaceAll("-", "");

                if(name.toLowerCase().contains("chi nhánh")){
                    name = name.substring(0,name.toLowerCase().indexOf("chi nhánh")) + "CN" + name.substring(name.toLowerCase().indexOf("chi nhánh")+"chi nhánh".length());
                }
                if(name.toLowerCase().contains("phòng giao dịch")){
                    name = name.substring(0,name.toLowerCase().indexOf("phòng giao dịch")) + "PGD" + name.substring(name.toLowerCase().indexOf("phòng giao dịch")+"phòng giao dịch".length());
                }
                if(name.toLowerCase().contains("thương mại cổ phần") ){
                    name = name.substring(0,name.toLowerCase().indexOf("thương mại cổ phần")) + "" + name.substring(name.toLowerCase().indexOf("thương mại cổ phần")+"thương mại cổ phần".length());
                }
                if(name.toLowerCase().contains("tmcp") ){
                    name = name.substring(0,name.toLowerCase().indexOf("tmcp")) + "" + name.substring(name.toLowerCase().indexOf("tmcp")+"tmcp ".length());
                }
                if(name.toLowerCase().contains("một thành viên")){
                    name = name.substring(0,name.toLowerCase().indexOf("một thành viên")) + "" + name.substring(name.toLowerCase().indexOf("một thành viên")+"một thành viên".length());
                }else if(name.toLowerCase().contains("mtv")){
                   name = name.substring(0,name.toLowerCase().indexOf("mtv")) + "" + name.substring(name.toLowerCase().indexOf("mtv")+"mtv".length());
                }
                if(name.toLowerCase().contains("tnhh")){
                    name = name.substring(0,name.toLowerCase().indexOf("tnhh")) + "" + name.substring(name.toLowerCase().indexOf("tnhh")+"tnhh".length());
                }
//                if(name.toLowerCase().contains("ngân hàng")){
//                    name = name.substring(0,name.toLowerCase().indexOf("ngân hàng")) + "NH" + name.substring(name.toLowerCase().indexOf("ngân hàng")+"ngân hàng".length());
//                }
            }
            return name;
        }
         
         // kết nối tt 5
         Connection connect = null;
         PreparedStatement pstm =  null;
         ResultSet rs = null;
    public Connection dbConnect(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://tt5/qlhs?useUnicode=true&characterEncoding=UTF-8";
            //connect = DriverManager.getConnection(url, "root", "root");
            // = DriverManager.getConnection("jdbc:mysql://195.195.200.186/qlhs?user=ttdk3&password=trungtam3");
            connect = DriverManager.getConnection("jdbc:mysql://tt5/qlhs?user=ttdk3&password=trungtam3");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaoPhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaoPhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TaoPhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TaoPhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
       // Lấy thông tin đơn từ quản lý hồ sơ
         public ArrayList<String> getThongTinPL(String madon,String day){
             ArrayList<String> data = new ArrayList<>();
             String sql = "SELECT bennhanbaodam as bnbd,benbaodam as bbd,"
                     + "concat(date_format(datepicker,'%d-%m-%Y'),' ',gio_nhan,':',phut_nhan) as ngaynhan "
                     + "FROM posts where  ma_loaihinhnhan = ? and datepicker = str_to_date(?,'%d-%m-%Y') and loaidon like 'CSGT%';";
             connect = dbConnect();
             try {
                 pstm =  connect.prepareStatement(sql);
                 pstm.setInt(1, Integer.parseInt(madon));
                 pstm.setString(2, day);
                 rs = pstm.executeQuery();
                 while(rs.next()){
                     System.out.println("NEXT");
                     String bnbd = rs.getString("bnbd");
                     String bbd = rs.getString("bbd");
                     String ngaynhap = rs.getString("ngaynhan");
                     data.add(bnbd);data.add(bbd);data.add(ngaynhap);
                 }
             } catch (SQLException ex) {
                 Logger.getLogger(TaoPhuLuc.class.getName()).log(Level.SEVERE, null, ex);
             }
             return data; 
         }
}
