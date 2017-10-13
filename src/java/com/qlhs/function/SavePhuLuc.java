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
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
import static org.openxmlformats.schemas.drawingml.x2006.main.STPenAlignment.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

/**
 *
 * @author ntdung
 */
public class SavePhuLuc {
    
    
    
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
    public static BufferedImage rotate90ToLeft( BufferedImage inputImage ){
//The most of code is same as before
	int width = inputImage.getWidth();
	int height = inputImage.getHeight();
	BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );
//We have to change the width and height because when you rotate the image by 90 degree, the

	for( int x = 0; x < width; x++ ) {
                            for( int y = 0; y < height; y++ ) {
                                    returnImage.setRGB(y, width - x - 1, inputImage.getRGB( x, y  )  );
                //Again check the Picture for better understanding
                            }
                    }
	return returnImage;

}
    
    
    public static ArrayList<NganHangBean> shortName(String path){
        ArrayList<NganHangBean> nhArr = new ArrayList<>();
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(path+"/data/banks.xml");
            Element classElement = document.getRootElement();
            List<Element> bankList = classElement.getChildren();
            for (int i = 0; i < bankList.size(); i++) {
                Element bankEle = bankList.get(i);
                NganHangBean nhb = new NganHangBean();
                nhb.setNHName(bankEle.getChildText("name"));
                nhb.setNHSName(bankEle.getChildText("sname"));
                nhArr.add(nhb);
            }
            
        } catch (JDOMException ex) {
            Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SavePhuLuc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nhArr;
    }
    
    public String tenLH(String name,String path){
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
            name = name.substring(0,name.toLowerCase().indexOf("thương mại cổ phần")) + "TMCP" + name.substring(name.toLowerCase().indexOf("thương mại cổ phần")+"thương mại cổ phần".length());
        }
        if(name.toLowerCase().contains("tmcp") ){
            name = name.substring(0,name.toLowerCase().indexOf("tmcp")) + "" + name.substring(name.toLowerCase().indexOf("tmcp")+"tmcp ".length());
        }
        if(name.toLowerCase().contains("một thành viên")){
            name = name.substring(0,name.toLowerCase().indexOf("một thành viên")) + "" + name.substring(name.toLowerCase().indexOf("một thành viên")+"một thành viên".length());
        }else if(name.toLowerCase().contains("mtv")){
           name = name.substring(0,name.toLowerCase().indexOf("mtv")) + "" + name.substring(name.toLowerCase().indexOf("mtv")+"mtv".length());
        }
        if(name.contains("tnhh")){
            name = name.substring(0,name.toLowerCase().indexOf("tnhh")) + "" + name.substring(name.toLowerCase().indexOf("tnhh")+"tnhh".length());
        }
        ArrayList<NganHangBean> nhArr = shortName(path);
        String subname = "";
        for (NganHangBean nhArr1 : nhArr) {
            String NHname = nhArr1.getNHName();
            // System.out.println("NH NAME = "+NHname );
            String sNHname = nhArr1.getNHSName();
            String NH = name.replaceAll(",", "");
            if(NH.contains("CN")){
                int cnIndex = NH.indexOf("CN");
                int nhIndex = 0;
                if(NH.toLowerCase().contains("ngân hàng")){
                    nhIndex = NH.toLowerCase().indexOf("ngân hàng");
                }else if(NH.contains("NH")){
                    nhIndex = NH.indexOf("NH");
                }
                System.out.println("TÊN = "+NH+", cnIndex = "+cnIndex+", nhIndex = "+nhIndex);
                if(cnIndex < nhIndex){
                    NH = NH.substring( cnIndex);
                }else{
                    NH = NH.substring(nhIndex,cnIndex);
                }
                
                if(NH.toLowerCase().contains("ngân hàng")){
                    NH = NH.substring(NH.toLowerCase().indexOf("ngân hàng")+"ngân hàng ".length());
                }
//                if(NH.toLowerCase().contains("tmcp")){
//                    NH = NH.substring(NH.toLowerCase().indexOf("tmcp ")+"tmcp ".length());
//                }
                if(NH.toLowerCase().contains("pgd")){
                    NH = NH.substring(0,NH.toLowerCase().indexOf("pgd"));
                }
            }
            if(NH.toLowerCase().trim().equals(NHname.toLowerCase())){
              //  s_name = sNHname+""+subname; 
              s_name = name.substring(0,name.toLowerCase().indexOf(NH.toLowerCase().trim())) + sNHname + name.substring(name.toLowerCase().indexOf(NH.toLowerCase().trim())+NH.toLowerCase().trim().length());
              if(s_name.toLowerCase().contains("ngân hàng")){
                  
                s_name  = s_name.substring(0,s_name.toLowerCase().indexOf("ngân hàng")) + "" + s_name.substring(name.toLowerCase().indexOf("ngân hàng")+"ngân hàng ".length());
              }
            }
            
        }
        }
        return s_name;
    }
    
    public static PhuLucBean getThongTinXe(String maonline) throws IOException{
           PhuLucBean plb = new PhuLucBean();
           String url ="https://dktructuyen.moj.gov.vn/dtn_str/search/public/";
           String sodonlandau = "";
           String thoidiemdangky = "";
           String benbaodam = "";
           String bnbd = "";
           plb.setSodondangky(maonline);
           ArrayList<ThongTinXe> ttxList = new ArrayList<>();
         try (final WebClient webClient = new WebClient()) {
            // webClient.getOptions().setThrowExceptionOnScriptError(false);
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
                               if(i==3){
                                org.jsoup.select.Elements tds =  TableElements.get(i).getElementsByTag("td");
                                
                                    ArrayList<ThongTinXe> data = new ArrayList<>();
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
                                    }
                                    plb.setThongtinXe(data);
                                }
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

    // get thong tin phu luc tu trang dang nhap
    public  PhuLucBean getTTXFromAdmin(String maonline,String username,String password) throws IOException{
        PhuLucBean plb = new PhuLucBean();
        String url ="https://dktructuyen.moj.gov.vn/admin";
        String sodonlandau = "";
        String thoidiemdangky = "";
        String benbaodam = "";
        String bnbd = "";
        plb.setSodondangky(maonline);
        
       ArrayList<ThongTinXe> ttxList = new ArrayList<>();
       try (final WebClient webClient = new WebClient()) {
           HtmlPage page = webClient.getPage(url);
           HtmlForm form = page.getFormByName("loginForm"); 
           form.getInputByName("login[username]").setValueAttribute(username); //works fine 
           HtmlInput passWordInput = form.getInputByName("login[password]");
         //   passWordInput.removeAttribute("disabled");
            passWordInput.setValueAttribute(password); 
            page = form.getInputByValue("Đăng nhập").click(); // works fine
            System.out.println(page.asText());
       }
        return plb;
    } 
   
   public XWPFDocument savePhuLuc05(String path,String maonline,String sk,String donid,int typecar,int loaidon,String loaixe,
           String biensocsgt,String madon,String csgtnameValue,String csgtdcValue,String footer,boolean nxe) throws InvalidFormatException, IOException, OutputException, BarcodeException, XmlException{
       XWPFDocument doc =  null;
            Barcode barcode = BarcodeFactory.createCode128C(donid);
            barcode.setBarHeight(30);
            barcode.setBarWidth(2);

            File imgFile = new File(path+"/barcode/"+donid+".jpg");
            
            //Write the bar code to PNG file
           BarcodeImageHandler.savePNG(barcode, imgFile);
            // savePhuluc5("testsize1.jpg");
            BufferedImage buffer = ImageIO.read(imgFile);

            BufferedImage newImg = rotate90ToLeft(buffer);
            File outputfile = new File(path+"/barcode/"+donid+".jpg");
            ImageIO.write(newImg, "jpg", outputfile);
            //String filedoc,String sodontt,String bnbd,String benbaodam,String ngaydangky,String donlandau,String shortname,
//            String loaidon1,String loaidon2,String loaidon3,String Loaixe,String sokhung,String somay,String bienso,String csgt,String csgtdc
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
            String csgt = csgtnameValue;
            String csgtdc = csgtdcValue;
            String pathImg = path+"/barcode/"+donid+".jpg";
            if(loaidon != 4){
                PhuLucBean plb = getThongTinXe(maonline);
                String dontt = plb.getSodondangky();
                String bnbd = plb.getBnbd();
                String benbaodam = plb.getBenbaodam();
                String ngaydangky = plb.getThoigiandangky();
                String shortname = tenLH(bnbd,path);
                int checkLength = sk.length();
                if(checkLength >0 ){
                    String[] sks = sk.split(",");
                    for(String sokhungkt : sks){
                        ArrayList<ThongTinXe> data = getThongTinXe(maonline).getThongtinXe();
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
                                pathImg = "demo";
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
                    ArrayList<ThongTinXe> data = getThongTinXe(maonline).getThongtinXe();
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
   
   // gộp nhiều phụ lục
   public XWPFDocument mergerFIleWord(XWPFDocument[] docSub) throws XmlException, IOException{
       XWPFDocument doc = docSub[0];
        if(docSub.length >1){
            doc = docSub[0];
            for(int i=1;i< docSub.length;i++){
                CTStyles c1 = doc.getStyle();
                int sizeC1 = c1.getStyleList().size();
                CTStyles ctsAdd = docSub[i].getStyle();
                int sizeAdd = ctsAdd.getStyleList().size();
                for(int j=0;j < sizeAdd;j++){
                    c1.addNewStyle();
                    c1.setStyleArray(sizeC1+i, ctsAdd.getStyleList().get(i));
                }
                doc.createStyles().setStyles(c1);
            }
        }
       return doc;
   }
   // CSGT
    public  ArrayList<ArrayList<String>> csgtInfor(String text,String path) throws JDOMException, IOException{
        text = text.toLowerCase();
         ArrayList<ArrayList<String>> csgtArr = new ArrayList<>();
//        String csgt = "";
//        String csgtdc = "";
//        File csgtF = new File("csgt.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(path+"data\\csgt.xml");
        Element classElement = document.getRootElement();
        List<Element> csgtList = classElement.getChildren("csgt");
        //System.out.println("----------------------------");
        for (int i = 0; i < csgtList.size(); i++) {
               Element node = (Element) csgtList.get(i);
               String nameCity = node.getAttributeValue("name");
               String bienso = node.getAttributeValue("id");
               if(nameCity.toLowerCase().contains(text) || bienso.contains(text)){
                   String name = node.getChild("phong").getChild("ten").getText();
                   String diachis = node.getChild("phong").getChild("diachi").getText();
                   String[] diachiAr = diachis.split("_");
                   for(String dc : diachiAr){
                       ArrayList<String> dt = new ArrayList<>();
                       dt.add(name);dt.add(dc);
                       csgtArr.add(dt);
                   }
                   String nameS = node.getChild("so").getChild("ten").getText();
                   String[] diachisS = node.getChild("so").getChild("diachi").getText().split("_");
                   for(String dc : diachisS){
                       ArrayList<String> dt = new ArrayList<>();
                       dt.add(nameS);dt.add(dc);
                       csgtArr.add(dt);
                   }
                   String nameCC = node.getChild("chicuc").getChild("ten").getText();
                   String[] diachisCC = node.getChild("chicuc").getChild("diachi").getText().split("_");
                   for(String dc : diachisCC){
                       ArrayList<String> dt = new ArrayList<>();
                       dt.add(nameCC);dt.add(dc);
                       csgtArr.add(dt);
                   }
                   if(node.getChild("doi") != null){
                       String nameD = node.getChild("doi").getChild("ten").getText();
                       String[] diachisD = node.getChild("doi").getChild("diachi").getText().split("_");
                    for(String dc : diachisD){
                        ArrayList<String> dt = new ArrayList<>();
                        dt.add(nameD);dt.add(dc);
                        csgtArr.add(dt);
                    }
                   }
                   if(node.getChild("cuc") != null){
                       String nameD = node.getChild("cuc").getChild("ten").getText();
                       String[] diachisD = node.getChild("cuc").getChild("diachi").getText().split("_");
                    for(String dc : diachisD){
                        ArrayList<String> dt = new ArrayList<>();
                        dt.add(nameD);dt.add(dc);
                        csgtArr.add(dt);
                    }
                   }
               }

            }
      //  System.out.println(csgtArr.size());
        for(ArrayList<String> dt : csgtArr){
            System.out.println(dt.get(0));
            System.out.println(dt.get(1));
        }
        return csgtArr;
    }
   
   public List<Object> getTongSoXe(String maonline) throws IOException{
       int number = 0;
       String url ="https://dktructuyen.moj.gov.vn/dtn_str/search/public/";
       List<Object> inforxe = new ArrayList<>();
       try (final WebClient webClient = new WebClient()) {
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
               if(div.getTextContent().contains(maonline)){
                   org.jsoup.nodes.Document doc = Jsoup.parse(div.asXml());
                  // removeComments(doc);
                   org.jsoup.select.Elements TableElements = doc.getElementsByTag("table");
                   org.jsoup.select.Elements trs =  TableElements.get(3).getElementsByTag("tr");
                   number = trs.size()-1;
                   inforxe.add(number);
                   for(int i=1;i< trs.size();i++){
                       String sk = trs.get(i).getElementsByTag("td").get(0).text();
                       System.out.println(sk);
                       inforxe.add(sk);
                   }
                   
               }
            }
       }
       return inforxe;
   }
}
