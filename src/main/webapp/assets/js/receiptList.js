function clickedEdit(receipt) {
	console.log("edit " );
}

var addEventHandlers = function() {
	$("#insert-receipt-modal").on("shown.bs.modal", function(e){
		$("input[name=date]").focus();
	});
	
	$("#insert-receipt-modal").on("hide.bs.modal", function(e){
		$("#removeFile").css("display","none");
		$('#cost').css("display","none");
		$('#uploadedImage').attr("src", "");
		$('#receiptId').val("");
		$("#insert-receipt-form")[0].reset();
	});
};

var enableButtons = function() {
	
	$("#add-receipt").click(function(){
		$("#insert-receipt-modal .modal-title").html("Add a Receipt");
		$("#insert-receipt-modal button[type=submit]").html("Add Receipt");
	});
	
	$('#confirm-delete').on('show.bs.modal', function(e) {
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
        
        $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
    });
	
	$(document).on("click", ".modifyReceipt", function () {
		$("#insert-receipt-modal .modal-title").html("Edit this Receipt");
		$("#insert-receipt-modal button[type=submit]").html("Edit Receipt");
		console.log("edit receipt " + $(this).data("id"));
		var id = $(this).data("id");
		$.ajax({
			type : "GET",
            url : 'getReceipt/'+id,
            data : JSON.stringify(id),
            timeout : 100000,
            beforeSend: function(xhr) {
		  		xhr.setRequestHeader("Accept", "application/json");
		  		xhr.setRequestHeader("Content-Type", "application/json");
		  	},
            success : function(data) {
            	console.log("success " + JSON.stringify(data));
            	var receipt = JSON.parse(JSON.stringify(data));
            	
            	$('#receiptId').val(receipt.receiptId);
            	$('#date').val(receipt.date);
            	$('#description').val(receipt.description);
            	$('#category').val(receipt.category);
            	$('#preCost').val(receipt.preCost);
                $('#dollarType').val(receipt.dollarType);
                
                if(receipt.dollarType != 'NZD') {
                	$('#cost').css("display","block");
                }
                $('#cost').val(receipt.cost);
                $('#claim').val(receipt.claim);
                $('#uploadedImage').attr("src", receipt.fileName);
                
                if(receipt.fileName) {
                	$("#removeFile").css("display","block");
                }
                $('#fileName').val(receipt.fileName);
                $('#insert-receipt-modal').modal('show');
            },
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
        });
	});
};

$(document).ready(function(){
	
	$.fn.serializeObject = function()
	{
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
	
	enableButtons();
	addEventHandlers();
	SaveReceipt();
	
	
});


function SaveReceipt() {
	
	$("#saveReceipt").on("click", function(){
		if(request) {
			// abort any pending request
			request.abort();
		}
		
		var $inputs = $('form#insert-receipt-form').find("input,textarea,select,button");
		var url = $(this).data("url");
		
		// serialize the data in the form ready for output
		var formData = JSON.stringify($('form#insert-receipt-form').serializeObject());
		
		$inputs.prop("disabled", true);
		
		$("#saveReceipt").html("Saving...");
		
		var request = $.ajax({
			type: 'POST',
	        url: url,
	        data: formData,
	        beforeSend: function(xhr) {
		  		xhr.setRequestHeader("Accept", "application/json");
		  		xhr.setRequestHeader("Content-Type", "application/json");
		  	},
	        success: function(data) {
	            $('#insert-receipt-modal').modal('hide');
	            
	            $('.alert-success span').html("Receipt has been created!");
	    		$('.alert-success').removeClass('hidden').addClass('show');
	    		
	    		$("#saveReceipt").html("Save changes");
	    		
	    		// reload receipts table
	    		$("#receiptTable").load("getReceiptList #receiptTable");
	        },
			error: function(e) {
				console.log("ERROR: ", e);
				$('.alert-warning span').html(data + ", Error: " + e);
				$('.alert-warning').removeClass('hidden').addClass('show');
			}
	    });
		
		request.always(function() {
			$inputs.prop("disabled", false); // re-enable the inputs
		});
	});
	
}

function showImage(imageFile) {
	$("#showImage").css({"background-image":"url('"+imageFile+"')", "width": "650px", "height": "600px", "display": "inline-block", "background-repeat":"no-repeat", "background-position":"center top"});
	$("#showImage").backgroundDraggable();
	$(".image").colorbox({
		inline:true, 
		open:true, 
		href:"#showImage",
		onCleanup:function() {
            $("#showImage").hide();
        }
	});
}

function setToClaimed(receiptId){
	layer.load();
	
	var token = $(this).data('token');
	$.ajax({
		url : 'setToClaimed', //server script to process data
		type : 'POST',
		data : {
			"receiptId" : receiptId
			, _token :token
		},
		success : function(data) {
			console.log("The data is " + data);
			if (data.indexOf('success') >= 0) {//
				console.log("success data : " + data);
				layer.closeAll('loading');
				location.reload();
			} else {
				layer.closeAll('loading');
				layer.msg("Error Message " + data, {
				    icon: 1,
				    time: 5000 // default close time is 3 seconds
				}, function(){
				});
				console.log("Error Message " + data);
			}
		},
		error : function(data, status, e) {
			layer.closeAll('loading');
			layer.msg("Error Message " + data, {
			    icon: 1,
			    time: 5000 // default close time is 3 seconds
			},function(){
			});
			console.log("Error Message " + data);
		}
	});
	
	
}