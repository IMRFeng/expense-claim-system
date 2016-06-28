$("#category").change(function() {
	if ($(this).val() == "0")
		$(this).addClass("empty");
	else
		$(this).removeClass("empty")
});

$("#category").change();


function getImage(data) {
    var img = new Image();
    this.img = img;
    var appname = navigator.appName.toLowerCase();
    if (appname.indexOf("netscape") == -1) {
       //ie
        img.onreadystatechange = function () {
            if (img.readyState == "complete") {
            	console.log("IE " + data);
                $("img[id='uploadedImage']").attr("src", data);
                $("#removeFile").show();
				
				layer.closeAll();
            }
        };
    } else {
       //firefox
        img.onload = function () {
            if (img.complete == true) {
            	console.log("Firefox " + data);
                $("img[id='uploadedImage']").attr("src", data);
                $("#removeFile").show();
				
				layer.closeAll();
            }
        }
    }
}

$(document).ready(function(){
	getImage.prototype.get = function (url) {
    	this.img.src = url;
	}

	$.ajax({
	    url: 'https://openexchangerates.org/api/latest.json?app_id=154aecaa5cef4616aa0886adef7ef10d',
	    dataType: 'json',
	    success: function(json) {
	        fx.rates = json.rates;
	        fx.base = json.base;
	    }
	});
		
	$("#removeFile").hide();
	
    $("#removeFile").click(function(){
		var fileName = $("#fileName").val();
		var removeConfirm = confirm("Are you sure you want to delete?");
		
		if(removeConfirm == true) {
			var token = $(this).data('token');
			$.ajax({
				url : 'removeReceiptPic', //server script to process data
				type : 'POST',
				data : {
					"fileName" : fileName
					, _token :token
				},
				success : function(data) {
					console.log("The data is " + data);
					if (data.indexOf('successMessage') >= 0) {//
						console.log("successMessage data : " + data);
						$("#fileName").val("");
						$("img[id='uploadedImage']").attr("src", "");
						$("#uploadFile").val("");
						$("#removeFile").hide();
					} else {
						console.log("error deleting file, " + data);
						$('#result').html('<font color="red">' + data + '!!</font>');
					}
				},
				error : function(data, status, e) {
					$('#result').html('<font color="red">Remove image failed, please try again!</font>');
				}
			});
		}
	});
	
	$("#dollarType").change(function() {
		var fromDollar = $('#dollarType option:selected').html();
		var preCost = $("#preCost").val();
		exchangeRates(preCost, fromDollar);
	});
	
	$("#preCost").change(function(){
		var fromDollar = $('#dollarType option:selected').html();
		var preCost = $("#preCost").val();
		exchangeRates(preCost, fromDollar);
	});
});

function exchangeRates(preCost, fromDollar) {
	if(preCost > 0) {
		if(fromDollar == 'NZD') {
			$("#cost").val(preCost);
			$("#cost").hide();
		} else {
			var nzd = fx(preCost).from('' + fromDollar + '').to("NZD");
			$("#cost").val(accounting.toFixed(nzd, 2));
			
			$("#cost").css({"width": "20%", "float": "left", "vertical-align": "bottom", "margin-left": "2px"});
  			$("#cost").show();
		}
  		
	} else {
		$("#cost").hide();
	}
}

function verifyPic() {
	$("img[id='uploadedImage']").attr("src", "");
	var f = $("#uploadFile").val();
	if (f == null || f == "") {
		$("#result").html("<span style='color:Red'>Error Msg:Please choose a file!</span>");
		return false;
	} else {
		var extname = f.substring(f.lastIndexOf(".") + 1, f.length);
		extname = extname.toLowerCase();
		if (extname != "jpeg" && extname != "jpg" && extname != "gif" && extname != "png") {
			$("#result").html("<span style='color:Red'>Error Msg: Wrong pic format, only supports: JPEG, JPG, GIF, PNG.</span>");
			return false;
		}
	}
	var file = document.getElementById("uploadFile").files;
	var size = file[0].size;
	if (size > 2097152) {
		$("#result").html("<span style='color:Red'>Error Msg: The pic is too big, no more than 2M!</span>");
		return false;
	}
	ajaxFileUpload();
}

function ajaxFileUpload() {
	
	$("#progressDiv").attr({
		value : 0,
		max : 100
	});
	layer.open({
	    type: 1,
	    content: $('#progressDiv'),
	    title: false,
	    closeBtn: false,
	    scrollbar: false
	});
	
	$("#progressDiv").show();
	
	$.ajaxFileUpload({
		url : 'uploadReceiptPic',
		secureuri : false,
		type: 'post',
		fileElementId : 'uploadFile',
		dataType : 'text',
		progress : true,
		success : function(data, status) {
			console.log("ok " + data);
			if (data.indexOf("Error Msg") == -1) {
				$("#fileName").val(data);
				
				var img = new getImage(data);
				img.get(data);
			} else {
				$('#result').html('<font color="red">' + data + '!!</font>');
			}
		},
		error : function(data, status, e) {
			console.log("error");
			$('#result').html('<font color="red">Upload image failed, please try again!</font>');
		}
	});
	
}
