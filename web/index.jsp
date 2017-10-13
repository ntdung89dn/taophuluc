<%-- 
    Document   : index
    Created on : Jul 6, 2017, 4:24:31 PM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <title>Phụ Lục 05</title>
        
        <style>
            #ui-datepicker-div .ui-datepicker-month {
                color: #000
            }
            #ui-datepicker-div .ui-datepicker-year{
                color: #000
            }
            body {
                background-color: #e0f3fa
            }
            .ui-state-hover, .ui-autocomplete tr:hover
            {
                color:White;
                background:#1c94c4;
                outline:none;
            }
        </style>
    </head>
    <body>
        <%
            String id = request.getParameter("id");
            String madon = "",bienso = "",loaixe = "1",typecar = "",sokhung = "",maonline = "",csgtname = "",csgtdc = "",footer="";
            String loaidon = "1";
            if(id != null){
                madon =  session.getAttribute("madon").toString();
                bienso = session.getAttribute("bienso").toString();
                loaixe = session.getAttribute("loaixe").toString();
                typecar = session.getAttribute("typecar").toString();
                sokhung = session.getAttribute("sokhung").toString();
                maonline = session.getAttribute("maonline" ).toString();
                csgtname = session.getAttribute("csgtname").toString();
                csgtdc = session.getAttribute("csgtdc" ).toString();
                footer = session.getAttribute("footer").toString();
                loaidon  = session.getAttribute("loaidon").toString();
            }
            %>
        <div id="wrapper">
            <div class="panel panel-default" style="margin: auto 0;">
                <div class="panel-heading">Thông tin phụ lục</div>
                <div class="panel-body" style="align-content: center;">
                    <!-- hàng đầu -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-2"><input type="text" id="pl_madon" placeholder="Nhập số đơn. vd : 4444" class="form-control" value="<%=madon%>"></div>
                        <div class="col-sm-2"><input type="text" id="pl_online" placeholder="Số Online : 125xxxx" class="form-control" value="<%=maonline%>"></div>
                       <!-- <div class="col-sm-2"><input type="text" id="pl_sophuluc" placeholder="Số phụ lục" class="form-control"></div> -->
                    </div><br>
                    <!-- hàng 2 -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-2"><input type="text" id="pl_bienso"  placeholder="Nhập biển số/ Tên (43, danang, đà nẵng)" class="form-control" value="<%= bienso%>"></div>
                        <div class="col-sm-2"><input type="text" id="pl_footer" placeholder="Kí hiệu đặt tại cuối trang" class="form-control" value="<%= footer%>"></div>
                        <div class="col-sm-2"><input type="text" id="pl_datepicker" class="form-control"></div>
                    </div><br>
                    <!-- hàng 2 -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-3"><input type="text" id="pl_csgtname"   class="form-control" value="<%= csgtname%>" readonly></div>
                        <div class="col-sm-3">
                            <input type="text" id="pl_csgtdc"   class="form-control" value="<%= csgtdc%>" readonly>
                        </div>
                    </div><br>
                    <!-- hàng  3 -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-2">
                            <select class="form-control" id="select_phuongtien">
                                <option value="1" <% if(Integer.parseInt(loaixe) ==1){ %> selected<% }%>>Xe ô tô</option>
                                <option value="2" <% if(Integer.parseInt(loaixe) ==2){ %> selected<% }%>>Rơ Móc</option>
                                <option value="3" <% if(Integer.parseInt(loaixe) ==3){ %> selected<% }%>>Tàu thủy</option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                      <!--      <input type="radio" name="rd_loaidon" value="1" checked> Đơn đăng ký
                            <input type="radio" name="rd_loaidon" value="2" >Đơn thay đổi
                            <input type="radio" name="rd_loaidon" value="3">Đơn rút bớt
                            <input type="radio" name="rd_loaidon" value="4">Đơn xóa -->
                            <select class="form-control" id="select_loaidon">
                                <option value="1" <% if(Integer.parseInt(loaidon) ==1){ %> selected<% }%>>Đơn đăng ký</option>
                                <option value="2" <% if(Integer.parseInt(loaidon) ==2){ %> selected<% }%>>Đơn thay đổi</option>
                                <option value="3" <% if(Integer.parseInt(loaidon) ==3){ %> selected<% }%>>Đơn rút bớt</option>
                                 <option value="4" <% if(Integer.parseInt(loaidon) ==4){ %> selected<% }%>>Đơn xóa</option>
                            </select> <span style="color: red">Chú ý: Hiện tại chỉ lấy được khung word(word 2007)</span>
                        </div>
                    </div><br>
                    <!-- hàng 4 -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-2">
                            <input type="text" id="pl_loaixe" placeholder="Loại xe" class="form-control" value="<%= typecar%>"> 
                            ** Lấy nhiều loại xe (loai_xe_1,loai_xe_2) 
                        </div>
                        <div class="col-sm-2">
                            <input type="text" id="pl_sokhung" placeholder="Số khung" class="form-control" value="<%= sokhung%>">
                            ** Lấy nhiều số khung (so_khung1,so_khung_2) 
                        </div>
                            <div class="col-sm-2">
                            <input type="checkbox" id="cb_nhieuxe">
                                ** Làm hết xe trong đơn.
                        </div>
                    </div><br>
                    <!-- hàng 5 -->
                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-3">
                            <div><span id="error" style="color: red"></span></div>
                            <div>Lưu phụ lục : <a href="#" onclick="return getDon()">Lấy file</a></div>
                        </div>
                        <div class="col-sm-2">
                        </div>
                        <div class="col-sm-4">
                            <div style="color: red;"> 
                                *** Chú ý : đọc hướng dẫn sử dụng tại <a target="_blank" href="${pageContext.request.contextPath}/file/Hướng dẫn sử dụng phần mềm Phụ Lục.pdf" >đây</a></div>
                                ****Phiên bản 1.1.1: <br>- Thay đổi đăng ký thế chấp thành đăng ký lần đầu ở đơn dk lần đầu
                                <br>- Sửa lỗi lấy nhiều đơn theo số khung.
                                <br> - Thay đổi rút gọn tên ngân hàng
                                <br>- Bỏ số đơn lần đầu trong đơn Xóa
                                
                        </div>
                      </div>
                </div>
              </div>
        
        
        <div id="dialog-confirm" title="Lấy thông tin đơn xóa">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Bạn đang chọn đơn xóa. Bạn muốn lấy Mẫu phụ lục hay lấy thông tin từ Quản Lý Hồ Sơ?</p>
        </div>
        <script>
      //      var days = 0;
        var date = new Date();
        var firstdate = new Date(date.getFullYear(), date.getMonth(), 1);
      //   var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
        var loailuu = "";
        $( "#dialog-confirm" ).dialog({
            autoOpen: false,
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
              "Mẫu": function() {
                  loailuu = "1";
                $( this ).dialog( "close" );
              },
              "Quản lý hồ sơ": function() {
                  loailuu = "2"; 
                $( this ).dialog( "close" );
              }
            }
          });
        $( "#pl_datepicker" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            }).datepicker("setDate", date);
            var clearFile = function(){
              $('#select_loaidon').val("1");  
              $('#pl_madon').val("");$('#pl_online').val("");
              $('#pl_bienso').val("");$('#pl_csgtname').val("");
              $('#pl_csgtdc').val("");$('#select_phuongtien').val(1);
              $('#pl_loaixe').val("");$('#pl_sokhung').val("");
            };
            function getDon(){
                var madon = $('#pl_madon').val().trim();
                if(madon === '' || madon === undefined){
                    $('#error').html("Chưa nhập mã số đơn");
                    return false;
                }
            
                var bienso = $('#pl_bienso').val().trim();
                if(bienso === '' || bienso === undefined){
                    $('#error').html("Chưa nhập biển số");
                    return false;
                }
                var maonline = $('#pl_online').val().trim();
                 if(maonline === '' || maonline === undefined){
                    $('#error').html("Chưa nhập số online");
                    return false;
                }
                var csgtname = $('#pl_csgtname').val();
                if(csgtname === '' || csgtname === undefined){
                    $('#error').html("Chưa chọn csgt");
                    return false;
                }
                var csgtdc = $('#pl_csgtdc').val();
                if(csgtdc === '' || csgtdc === undefined){
                    $('#error').html("Chưa chọn csgt");
                    return false;
                }
                var ngaygio = $('#pl_datepicker').val().split("-");
                var ngaygiosearch = ngaygio[2]+"-"+ngaygio[1]+"-";
                var loaixe = $('#select_phuongtien').val();
                var typecar = $('#pl_loaixe').val();
                if(typecar === undefined){
                    typecar = "";
                }
                var sokhung = $('#pl_sokhung').val();
                var loaidon = $('#select_loaidon').val();
           /**     var sophuluc = $('#pl_sophuluc').val();
                if(sophuluc === '' || sophuluc === undefined){
                    $('#error').html("Chưa nhập số phụ lục");
                    return false;
                } **/
                var footer = $('#pl_footer').val();
               if(footer === '' || footer === undefined){
                    $('#error').html("Chưa nhập ký hiệu");
                    return false;
                }
                var cb_nxeCheck = $('#cb_nhieuxe').is(":checked");
              //  console.log("NX = "+cb_nxeCheck);
                csgtname = csgtname.replace("&","%26");
                
                if(cb_nxeCheck){
                    window.open("./phuluc05.jsp?madon="+madon+"&bienso="+bienso+"&loaixe="+loaixe
                                    +"&typecar="+typecar+"&ngaygio="+ngaygiosearch+"&sokhung=all&loaidon="+loaidon+"&maonline="+maonline
                                    +"&csgtname="+csgtname+"&csgtdc="+csgtdc+"&footer="+footer,"_blank");
                }else{
                    var so_khungs = sokhung.split(",").length;
                    if(so_khungs >1){
                        var numSodon = madon.split(",").length;
                        if(numSodon !== so_khungs){
                            $('#error').html("Số lượng đơn và số lượng số khung không bằng nhau");
                            return false;
                        }else{
                            sokhung = sokhung.replace(/,/g, '%2C');
                            madon = madon.replace(/,/g, '%2C');
                            typecar = typecar.replace(/,/g, '%2C');
                            window.open("./phuluc05.jsp?madon="+madon+"&bienso="+bienso+"&loaixe="+loaixe
                                    +"&typecar="+typecar+"&ngaygio="+ngaygiosearch+"&sokhung="+sokhung+"&loaidon="+loaidon+"&maonline="+maonline
                                    +"&csgtname="+csgtname+"&csgtdc="+csgtdc+"&footer="+footer,"_blank");
                        }
                    }else{
                        window.location = "SavePhuLuc?action=download&madon="+madon+"&bienso="+bienso+"&loaixe="+loaixe
                            +"&typecar="+typecar+"&ngaygio="+$('#pl_datepicker').val()+"&sokhung="+sokhung+"&loaidon="+loaidon+"&maonline="+maonline
                            +"&csgtname="+csgtname+"&csgtdc="+csgtdc+"&footer="+footer+"&loailuu="+loailuu;
                    }
                    
                }
                clearFile();
              //  }
                
                
             //  window.location.href = fileUrl; 
              /**  xhr.onreadystatechange = function() {
                    console.log(xhr.readyState);
                    console.log(xhr.status);
                    if( xhr.readyState === 4 || xhr.readyState === 200) {
                        console.log(xhr.status);
                        if(xhr.status === 200){
                            window.location.href = fileUrl;
                        }else{
                            fileUrl = "404.jsp";
                            window.location.href = fileUrl;
                        }
                        
                    }
                };
                
                xhr.open('GET',fileUrl);
               xhr.send(null); **/

              /**  $.ajax({
                            url : "SavePhuLuc",
                            type : "GET",
                            dataType: 'binary',
                            data: {action: "download",madon:madon, bienso : bienso,loaixe : loaixe, typecar : typecar,ngaygio : ngaygiosearch,
                            sokhung : sokhung,loaidon : loaidon,maonline : maonline,csgtname : csgtname,csgtdc : csgtdc,footer : footer},
                            success : function(data) {
                            }

                    });**/
             //   clearFile();
            };
            $(document).on('change','#select_loaidon',function(){
               if($(this).val() === '4') {
                   $( "#dialog-confirm" ).dialog('open');
               }
            });
            var csgtinfor ={     
                    source : function(request, response) {
                        $.ajax({
                                url : "SavePhuLuc?action=loadcsgt",
                                type : "GET",
                                data : {
                                        csgt : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                    //    console.log(data);
                                        response(data);
                                }
                        });
                    },
                    select: function (event, ui) {
                        $("#pl_csgtname").val( ui.item.name );
                        $('#pl_csgtdc').val( ui.item.diachi );
                        //$(this).autocomplete('close');
                       // $(".ui-menu-item").hide();
                        return false;
                    }
                };
                
                var csgtAuto = $('#pl_bienso').autocomplete(csgtinfor).data('ui-autocomplete');
                csgtAuto._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table border='1'><thead><tr><th>Tên CSGT</th><th>Địa Chỉ</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 csgtAuto._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  csgtAuto._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.diachi +"</td>" )
                      .appendTo( table );
                  };
        </script>
    </body>
</html>
