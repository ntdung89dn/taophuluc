<%-- 
    Document   : phuluc05
    Created on : Jun 28, 2017, 11:05:59 AM
    Author     : ntdung
--%>

<%@page import="com.qlhs.function.PhuLucBean"%>
<%@page import="com.qlhs.function.TaoPhuLuc"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
      <!--  <script src="../js/jquery-barcode.js"></script> -->
        <style type="text/css">        
            html, body {            margin: 0;            padding: 0;            font-family: "Times New Roman";            font-size: 13pt;            background-color: #eee;        }
            * {            box-sizing: border-box;            -moz-box-sizing: border-box;        } 
            .page {            margin: 1cm auto;            background: #fff;            box-shadow: 0 4px 5px rgba(75, 75, 75, 0.2);            outline: 0;        } 
            div.page-break {            page-break-after: always;        }        
            h1 {            page-break-before: always;        }       
            h1, h2, h3, h4, h5, h6 {            page-break-after: avoid;        }       
            p {            margin: 0;            text-align: justify;        }        
            a {            text-decoration: none;            color: black;        }       
            table {            page-break-inside: avoid;        }       
            @page {            orphans: 4;            widows: 2;        }        
            @media print {            html, body {                background-color: #fff;            }          
              .page {                
                  width: initial !important;               
                  min-height: initial !important;              
                  margin: 0 !important;                
                  padding: 0 !important;               
                  border: initial !important;                
                  border-radius: initial !important;                
                  background: initial !important;                
                  box-shadow: initial !important;                
                  page-break-after: always;            
              }        
            }        
            /* CSS for A4 page*/        
            .page {            
                width: 21cm;            
                min-height: 29.7cm;            
                padding-left: 2cm;           
                padding-top: 2cm;            
                padding-right: 2cm;            
                padding-bottom: 2cm;        
            }        
            @page {            
                size: A4 portrait;           
                margin-left: 2cm;            
                margin-top: 2cm;            
                margin-right: 2cm;            
                margin-bottom: 2cm;        
            }        
            table.table-border {            
                border-collapse: collapse;       
            }       
            table.table-border td, table th { 
                border: 1pt solid windowtext;       
            }        
            table.table-border td {            
                padding: 0.1cm;        
            }        
            table.table-border td p 
            {            
                text-align: left;        
            }        
            ul.branch-info { 
                list-style: none;            
                margin-top: 0;        
            }        
            ul.branch-info li:before{
                content: "- ";        
            }    
            .bc_print{
                z-index: -1;
            }
           select#print_bbd_maso{
                border:0px;
                outline:0px;
                 border:0px;
                outline:0px;
                -webkit-appearance: none;
                 -moz-appearance: none;
                 text-indent: 1px;
                 text-overflow: '';
            }
            .qpSend a {
                background: url(../images/btmSend.jpg) no-repeat;
                width: 71px;
                height: 30px;
                display: block;
                line-height: 30px;
                text-decoration: none;
                text-align: center;
                color: #fff;
                display: block;
                float: left;
                margin-left: 10px;
            }
        </style>
        <title>Phụ Lục 05</title>
    </head>
    <body class="document">
        <%
           //     int id = Integer.parseInt(request.getParameter("id"));
                 String madon = request.getParameter("madon");
                int loaixe = Integer.parseInt(request.getParameter("loaixe"));
                String typecar = request.getParameter("typecar");
                String ngaygio = request.getParameter("ngaygio");
                ngaygio = ngaygio.substring(ngaygio.indexOf("-")+1);
                String sokhungvalue = request.getParameter("sokhung");
                String maonline = request.getParameter("maonline");
                 String csgtname= request.getParameter("csgtname");
         //    System.out.println(csgtname);
                String csgtdiachi = request.getParameter("csgtdc");
                int loaidon = Integer.parseInt(request.getParameter("loaidon"));
                int pagePL = madon.split(",").length;
                TaoPhuLuc tpl = new TaoPhuLuc();
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
          //   String[] ldon = {loaidon,loaidon,loaidon,loaidon};
            //    System.out.println("SIZE = "+new ChangeNumberToText().getLoaiDon(loaidon).length);
                PhuLucBean plb = tpl.getThongTinXe(maonline);
                String benbaodam = plb.getBenbaodam();
                String bnbd = plb.getBnbd();
                String thoigiandk = plb.getThoigiandangky();
                String sodonlandau = plb.getDonlandau();
                String bnbdShort = tpl.tenLH(bnbd);
                String motats = plb.getTauca();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                //getTime() returns the current date in default time zone
                Date date = calendar.getTime();
                String day = String.valueOf(calendar.get(Calendar.DATE));
                if(day.length() <2){
                    day = "0"+day;
                }
                //Note: +1 the month for current month
                String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                if(month.length() <2){
                    month = "0"+month;
                }
                int year = calendar.get(Calendar.YEAR);
                if(sokhungvalue.equals("all")){
                    pagePL = plb.getThongtinXe().size();
                }
                System.out.println("MA DON = "+madon);
                for(int i=0;i< pagePL;i++){
                    String sokhung = "";
                    String somay = "";
                    String bienso = "";
                     if(!sokhungvalue.equals("all")){
                     //    System.out.println("SO KHUNG = "+sokhungvalue);
                         boolean check = false;
                                for(int j=0;j< plb.getThongtinXe().size();j++){
                                    if(!check){
                                        if(plb.getThongtinXe().get(j).getSoKhung().contains(sokhungvalue.split(",")[i])){
                                          //  System.out.println("CHECKING = true");
                                            sokhung = plb.getThongtinXe().get(j).getSoKhung();
                                            somay = plb.getThongtinXe().get(j).getSoMay();
                                            bienso = plb.getThongtinXe().get(j).getBienSo();
                                            check = true;
                                        }
                                    }
                                }
                            
                     }else{
                            sokhung = plb.getThongtinXe().get(i).getSoKhung();
                            somay = plb.getThongtinXe().get(i).getSoMay();
                            bienso = plb.getThongtinXe().get(i).getBienSo();
                     }
                    
                   String maPLStr = "";
                   String tenloaixe = "";
                   System.out.println("TEN LOAI Xe = "+typecar);
                   if(madon.contains(",")){
                        maPLStr = madon.split(",")[i];
                        if(typecar.split(",").length > i){
                            tenloaixe = typecar.split(",")[i];
                            System.out.println("TEN LOAI Xe = "+tenloaixe);
                        }
                        
                   }else{
                       maPLStr = ""+ (Integer.parseInt(madon)+i);
                       tenloaixe = typecar;
                   }
            %>
            
            <div class="page">
            <div class="bc_print" style="margin-left: -20px;margin-top: 10px; font-size: 0px; background-color: #FFFFFF;"></div>
            <p style="text-transform: uppercase; text-align: center; font-weight: bold;">Cộng hòa xã hội chủ nghĩa Việt Nam</p>
            <p style="text-align: center; font-weight: bold;">Độc lập - Tự do - Hạnh phúc</p>
            <hr style="width: 35%; margin: 0 auto; color: black; display: block;">
            <p style="margin-top: 1cm"></p>
            <p style="text-align: center; font-weight: bold; font-size: 14pt; text-transform: uppercase;">
                VĂN BẢN THÔNG BÁO VỀ VIỆC <%=titledon %><br>PHƯƠNG TIỆN GIAO THÔNG                                            </p>
            <hr style="width: 35%; margin: 0 auto; color: black; display: block;">
            <p style="margin-top: 0.5cm"></p>
            <table width="100%" style="border: none;">
                <tbody>
                <tr style="border: none; font-size: 12pt; font-style: italic;">
                    <td style="text-align: left; vertical-align: top; border: none;" width="50%">
                        Số: <p class="pl_online"  contenteditable="true" style="display: inline">nhapmaonline</p>     /TB-TT3               </td>
                    <td style="text-align: right; vertical-align: top; border: none;" width="50%">
                                                                                                    Đà Nẵng, ngày <%=day%> tháng <%=month%> năm <%=year%>                                            </td>
                </tr>
                </tbody>
            </table>
            <p style="margin-top: 0.5cm"></p>
            <div style="text-indent: 3cm;"style="display: inline">Kính gửi: 
                <div contenteditable="true" style="display: inline"
                     <%
                      if(i==0){
                                %>
                                id="csgt_name"
                                <%
                            }
                        %>
                     class="csgt_name" ><%=csgtname%>
                </div></div>
            <div style="text-indent: 3cm;" >Địa chỉ: <div contenteditable="true" style="display: inline"
                 <%
                      if(i==0){
                                %>
                                id="csgt_diachi"
                                <%
                            }
                        %>
                                                          class="csgt_diachi"><%=csgtdiachi%></div> </div>
            <p style="text-indent: 1cm;">
                Thực hiện nhiệm vụ trao đổi thông tin về tình trạng pháp lý của tài sản, Trung tâm đăng ký giao dịch, tài sản tại Đà Nẵng (sau đây gọi là Trung tâm Đăng ký) gửi thông báo về việc <span class="loai_don_1"><%=ldon[0]%></span> phương tiện giao thông với nội dung như sau:            </p>
            <%
                if(loaixe ==1){
                %>
            <table width="100%" class="table-border">
                <tbody><tr>
                    <th colspan="3" width="45%">Phương tiện</th>
                    <th>Chủ sở hữu (Bên bảo đảm)</th>
                    <th>Loại đăng ký</th>
                    <th>Số đơn đăng ký lần đầu</th>
                </tr>
                <tr>
                    <td rowspan="3" contenteditable="true"><%=tenloaixe%></td>
                    <td width="15%" contenteditable="true">Số khung</td>
                    <td contenteditable="true"><%=sokhung%></td>
                    <td rowspan="3" contenteditable="true"><%=benbaodam %></td>
                    <td rowspan="3" align="center">
                        <p class="loai_don_2" contenteditable="true" style="text-align: center;"><%=ldon[1]%></p>
                        <br><strong>Ngày giờ đăng ký: <br>
                        </strong><%=thoigiandk%><br><strong>
                            Thế chấp tại: </strong>
                        <p style="text-align: center;" contenteditable="true"><%=bnbd %></p>
                        <p class="loai_don_3" style="text-align: center;">(<%=ldon[2]%>)</p>
                    </td>
                    <td rowspan="3" contenteditable="true"><%=sodonlandau%></td>
                </tr>
                <tr>
                    <td contenteditable="true">Số máy</td>
                    <td contenteditable="true"><%= somay %></td>
                </tr>
                <tr>
                    <td contenteditable="true">Biển số</td>
                    <td contenteditable="true"><%= bienso %></td>
                </tr>
            </tbody>
            </table>
                    <%
                        }else if(loaixe==2){
                        %>
                    <table width="100%" class="table-border">
                        <tbody><tr>
                            <th colspan="3" width="45%">Phương tiện</th>
                            <th>Chủ sở hữu (Bên bảo đảm)</th>
                            <th>Loại đăng ký</th>
                            <th>Số đơn đăng ký lần đầu</th>
                        </tr>
                        <tr>
                            <td rowspan="2" contenteditable="true"><%=tenloaixe%></td>
                            <td width="15%" contenteditable="true">Số khung</td>
                            <td contenteditable="true"><%=sokhung%></td>
                            <td rowspan="2" contenteditable="true"><%=benbaodam %></td>
                            <td rowspan="2" align="center">
                                <p class="loai_don_2" contenteditable="true" style="text-align: center;"><%=ldon[1]%></p>
                                <br><strong>Ngày giờ đăng ký: <br>
                                </strong><%=thoigiandk%><br><strong>
                                    Thế chấp tại: </strong>
                                <p style="text-align: center;" contenteditable="true" ><%=bnbd %></p>
                                <p class="loai_don_3" style="text-align: center;">(<%=ldon[2]%>)</p>
                            </td>
                            <td rowspan="2"><%=sodonlandau%></td>
                        </tr>
                        <tr>
                            <td contenteditable="true">Biển số</td>
                            <td contenteditable="true"><%= bienso %></td>
                        </tr>
                    </tbody>
                    </table>
                        <%
                        }else if(loaixe ==3){
                        %>
                        <table width="100%" class="table-border">
                        <tbody><tr>
                            <th colspan="2" width="45%">Phương tiện</th>
                            <th>Chủ sở hữu (Bên bảo đảm)</th>
                            <th>Loại đăng ký</th>
                            <th>Số đơn đăng ký lần đầu</th>
                        </tr>
                        <tr>
                            <td width="10%" contenteditable="true" style="max-width: 10%;"><%=tenloaixe%></td>
                            <td width="35%" contenteditable="true"><%=motats%></td>
                            <td contenteditable="true"><%=benbaodam %></td>
                            <td  align="center">
                                <p class="loai_don_2" contenteditable="true" style="text-align: center;"><%=ldon[1]%></p>
                                <br><strong>Ngày giờ đăng ký: <br>
                                </strong><%=thoigiandk%><br><strong>
                                    Thế chấp tại: </strong>
                                <p style="text-align: center;" contenteditable="true"><%=bnbd %></p>
                                <p class="loai_don_3" style="text-align: center;">(<%=ldon[2]%>)</p>
                            </td>
                            <td rowspan="2" contenteditable="true"><%=sodonlandau%></td>
                        </tr>
                    </tbody>
                    </table>
                        <%
                        }
                        %>
            <p style="margin-top: 0.5cm"></p>
            <p style="text-indent: 1cm;">
                Trung tâm Đăng ký đề nghị Quý cơ quan cập nhật thông tin đối với phương tiện giao thông nêu trên để quản lý theo quy định của pháp luật và phản hồi kết quả cập nhật thông tin cho Trung tâm Đăng ký theo địa chỉ:            </p>
            <ul class="branch-info">
                <li>Trung tâm đăng ký giao dịch, tài sản tại Đà Nẵng</li>
                <li>Địa chỉ: 109 Hoàng Sỹ Khải, phường An Hải Bắc, quận Sơn Trà, TP. Đà Nẵng</li>
                <li>Thư điện tử: trungtamdangkydanang@gmail.com</li>
            </ul>
            <table style="border: none;" width="100%">
                <tbody>
                <tr style="border: none;">
                    <td width="50%" style="border: none;">
                        <p style="font-style: italic; font-weight: bold; font-size: 12pt">Nơi nhận:</p>
                        <p style="font-size: 11pt">- Như trên;</p>
                        <p style="font-size: 11pt" contenteditable="true">- <%= bnbdShort %></p>
                        <p style="font-size: 11pt">- Lưu: VT (<span style="color: red;font-weight: bold;" contenteditable="true"><%=maPLStr%></span>).</p>
                    </td>
                    <td width="50%" style="border: none; vertical-align: top;">
                        <p style="text-align: center; vertical-align: top; font-size: 14pt">
                            <strong style="text-transform: uppercase;" contenteditable="true">Giám đốc</strong>
                        </p>
                        <p style="font-style: italic; text-align: center; margin-top: 0">(Ký, ghi rõ họ tên, đóng dấu)</p>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
           <%
               }
               %>
      <!--     <div id="dialog-confirm" title="Chọn loại đơn thay đổi?">
                <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Đây là loại đơn thay đổi,xin chọn loại đơn thay đổi !</p>
              </div> -->
        <script>
          /**  $( "#dialog-confirm" ).dialog({
                    autoOpen: false,
                    resizable: false,
                    height: "auto",
                    width: 400,
                    modal: true,
                    buttons: {
                      "Thay đổi": function() {
                          $('.loai_don_1').html("Thế chấp");
                          $('.loai_don_2').html('Thế chấp');
                          $('.loai_don_3').html('Đăng ký thay đổi');
                        $( this ).dialog( "close" );
                      },
                      "Xóa": function() {
                          $('.loai_don_1').html("Xóa thế chấp");
                          $('.loai_don_2').html('Xóa thế chấp');
                          $('.loai_don_3').html('(Rút bớt tài sản)');
                        $( this ).dialog( "close" );
                      }
                    }
                  });
                $.urlParam = function(name){
                    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                    if (results===null){
                       return null;
                    }
                    else{
                       return results[1] || 0;
                    }
                };
                
            /**      var upateGioHL = function(){
                    var giohl = $('#print_giohl').val();
                    var id = $.urlParam('id'); 
                    $.ajax({
                        type: "GET",
                        url:"../LoadDonAjax",
                        data:{"action":"updategiohl",giohl: giohl,donid: id},
                        success: function (data) {
                        }
                    }); 
                };
                
                var updatePhutHL = function(){
                    var phuthl = $('#print_phuthl').val();
                    var id = $.urlParam('id'); 
                    $.ajax({
                        type: "GET",
                        url:"../LoadDonAjax",
                        data:{"action":"updatephuthl",phuthl: phuthl,donid: id},
                        success: function (data) {
                        }
                    }); 
                };
                
              var loadBarcode = function(id){
                    var id = $.urlParam('id'); 
                    $(".bc_print").barcode(id, "code128",{barWidth:2, barHeight:30});
                }; **/
          /**      var savePhuLuc = function(){
                    var donid = $('#donid').val();
                    var maonline = $('#maplonline').html();
                    var pagePL = $('#page_num').val();
                    var mapl = $('#mapl').val();
                    var loaidon = $('#checkloaidon').val();
                  //  console.log(donid);console.log(maonline);
                    $.ajax({
                        type: "GET",
                        url:"../LoadOnlineServlet",
                        data:{"action":"savephuluc",donid: donid,maonline: maonline,page:pagePL,mapl: mapl,loaidon: loaidon},
                        success: function (data) {
                          //  console.log(data);
                            $('.qpSend').hide();
                            if(data === 'FAILED'){
                                console.log(data);
                            }else{
                                var barcode = data.split("-");
                                $('.bc_print').each(function(i){
                                    $(this).barcode(barcode[i], "code128",{barWidth:2, barHeight:30});
                                });
                            }
                        }
                    }); 
                }; **/
         /**       (function checkLD(){
                   var checkloaidon = $('#checkloaidon').val();
                   if(checkloaidon.toLowerCase() ==='đăng ký thay đổi'){
                       $("#dialog-confirm").dialog('open');
                   }  
                })(); 
                $('#maplonline').on('keyup',function(){
                    //$(this).val();
                    var maonline  = $(this).html();
                   $('.pl_online').each(function(i){
                        if($(this).prop("id") !=='maplonline'){
                        //    console.log("111");
                            $(this).html(maonline);
                        }
                   });
                });**/
                
           /**     var addCSGT =  function(){
                    var name = $('#csgt_name').html();
                    var diachi = $('#csgt_diachi').html();
                    var donid = $('#donid').val();
                    $.ajax({
                        type: "GET",
                        url:"../LoadOnlineServlet",
                        data:{"action":"addcsgt",donid: donid,name: name,diachi: diachi},
                        success: function (data) {
                            
                        }
                    }); 
                }; **/
                $('#csgt_name').on('DOMSubtreeModified',function(){
                    var name = $(this).html();
                    $('.csgt_name').each(function(i){
                        if($(this).prop('id') !=='csgt_name'){
                            $(this).html(name);
                           //  addCSGT();
                        }
                    });
                });
                $('#csgt_diachi').on('DOMSubtreeModified',function(){
                    var diachi = $(this).html();
                    $('.csgt_diachi').each(function(i){
                        if($(this).prop('id') !=='csgt_diachi'){
                            $(this).html(diachi);
                        //    addCSGT();
                        }
                    });
                });
     //           $('#print_giohl').on('keyup',upateGioHL);
        //        $('#print_phuthl').on('keyup',updatePhutHL);
            //    loadBarcode();
               /** var loadBarCode = function(){
                    var donid = $('#donid').val();
                    console.log(donid);
                    $.ajax({
                        type: "GET",
                        url:"../LoadOnlineServlet",
                        dataType: 'json',
                        data:{"action":"getbarcode",barcode : donid},
                        success: function (data) {
                            
                            var mabarcode = data.barcode;
                            console.log(mabarcode);
                            var maonlinepl = data.maonlinepl;
                            console.log(maonlinepl);
                            $('.pl_online').html(maonlinepl);
                            $('.bc_print').each(function(i){
                                if(mabarcode.split("-").length > i){
                                    $(this).barcode(mabarcode.split("-")[i], "code128",{barWidth:2, barHeight:30});
                                }
                            });
                        }
                    }); 
                };
                loadBarCode(); **/
                  
            </script>
    </body>
</html>
