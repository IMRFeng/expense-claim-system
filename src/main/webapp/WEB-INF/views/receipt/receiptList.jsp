<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/colorbox.css" />

<script src="<%=request.getContextPath()%>/assets/js/jquery.colorbox.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/draggable_background.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/layer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/ajaxfileupload.js"></script><!-- AjaxFileUpload-1.0.0.min.js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/money.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/accounting.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/createReceipt.js"></script>
<style type="text/css">
.btn.active.focus, .btn.active:focus, .btn.focus, .btn:active.focus, .btn:active:focus, .btn:focus {
    outline: 0;
}

.btn.active, .btn:active {
    background-color: #3071a9;
}
</style>
</head>
<body>
<fmt:setLocale value="en_NZ"/>

<div class="col-md-10 col-sm-10">
	<div class="alert alert-success hidden">
	   <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	   <strong>Success!</strong> <span></span>.
	</div>
	<div class="alert alert-warning hidden">
	   <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	   <strong>Warning!</strong> <span></span>.
	</div>
	<div class="row">
		<div class="col-sm-4">
			<h4>List of Claims</h4>
		</div>
		<div class="col-sm-8">
			<spring:url value="/addReceipt" var="addReceiptUrl" htmlEscape="true" /> 
			<button class="btn btn-primary pull-right" id="add-receipt" data-toggle="modal" data-target="#insert-receipt-modal">Add Receipt</button>
		</div>
	</div>
	
	<table class="table table-hover table-bordered table-striped table-responsive text-center" id="receiptTable">
		<tr>
			<td class="heading">Claimed?</td>
			<td class="heading">Id</td>
			<td class="heading">Date</td>
			<td class="heading">Description</td>
			<td class="heading">Category</td>
			<td class="heading">Cost (NZD)</td>
			<td class="heading">Cost Occured Country</td>
			<td class="heading">Legitimate Expense</td>
			<td class="heading">Thumbnail</td>
			<td class="heading"></td>
		</tr>
		<tfoot><tr><td class="last" colspan="10"></td></tr></tfoot>
		<c:set var="totalClaimable" scope="request" value="0"/>
		<c:set var="totalCost" scope="request" value="0"/>
		<c:forEach var="receipt" items="${receiptList}">
			<c:set var="totalCost" scope="request" value="${receipt.cost + totalCost}"/>
				<tr>
				<td>
					<c:choose>
						<c:when test="${receipt.claim eq 'No'}">
							<input type="checkbox" id="${receipt.receiptId}" disabled/>
						</c:when>
						<c:otherwise>
						    <c:set var="totalClaimable" scope="request" value="${receipt.cost + totalClaimable}"/>
							<input type="checkbox" id="${receipt.receiptId}" onclick="setToClaimed('${receipt.receiptId}')"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td>${receipt.receiptId}</td>
				<td>${receipt.date}</td>
				<td title="${receipt.description}">
					<c:set var="desc" value="${receipt.description}"/>
					<c:choose>
						<c:when test="${fn:length(desc) > 12}">
							<c:out value="${fn:substring(desc, 0, 12)}..." />
						</c:when>
						<c:otherwise>
							${receipt.description}
						</c:otherwise>
					</c:choose>
				</td>
				<td>${receipt.category}</td>
				<td><fmt:formatNumber value="${receipt.cost}" type="currency"/></td>
				<td>
					<c:choose>
						<c:when test="${receipt.dollarType eq 'NZD'}">
							NZ
						</c:when>
						<c:when test="${receipt.dollarType eq 'AUD'}">
							AU(<fmt:formatNumber value="${receipt.preCost}" type="currency"/>)
						</c:when>
						<c:otherwise>
							US(<fmt:formatNumber value="${receipt.preCost}" type="currency"/>)
						</c:otherwise>
					</c:choose>
				</td>
				<td>${receipt.claim}</td>
				<td>
					<c:if test="${receipt.fileName != ''}">
						<!-- getReceiptImage/<c:out value="${receipt.receiptId}"/> -->
						<a href="#showImage" class="image" onclick="showImage('<c:out value="${receipt.fileName}"/>')">
							<c:set var="originalFile" value="${receipt.fileName}"/>
							<c:set var="thumbnail" value="_thumbnail.jpg"/>
							<c:set var="dotPosition" value="${fn:indexOf(originalFile, '.')}"/>
							<c:set var="fileName" value="${fn:substring(originalFile, 0, dotPosition)}" />
							<img alt="" title="Show dragable original image" src="${fileName}${thumbnail}" onError="this.onerror=null;this.src='';"/>
							<!-- <img alt="" src="getReceiptThumbnail/<c:out value="${receipt.receiptId}"/>" onError="this.onerror=null;this.src='';"/> -->
							
						</a>
					</c:if>
				</td>
				<td>
					<button data-id="${receipt.receiptId}" class="btn btn-xs btn-success modifyReceipt"><span class="glyphicon glyphicon-pencil"></span></button>
					<button class="btn btn-xs btn-danger removeReceipt" data-href="<%=request.getContextPath()%>/delete?id=${receipt.receiptId}" data-toggle="modal" data-target="#confirm-delete">
				        <span class="glyphicon glyphicon-remove"></span>
				    </button>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCost > 0}">
			<tr>
				<td colspan="5" class="text-right">Total of Legitimate Expense:</td>
				<td colspan="5" class="text-left">
					<fmt:formatNumber value="${totalClaimable}" type="currency"/>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="text-right">Total Amount:</td>
				<td colspan="5" class="text-left">
					<fmt:formatNumber value="${totalCost}" type="currency"/>
				</td>
			</tr>
		</c:if>
	</table>
</div>
<!-- Modal -->

 <!-- Modal -->
<div class="modal" id="insert-receipt-modal" tabindex="-1" role="dialog" aria-labelledby="insert-receipt-label" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <button type="button" class="close" 
                   data-dismiss="modal">
                       <span aria-hidden="true">&times;</span>
                       <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="insert-receipt-label">
                    Add Receipt
                </h4>
            </div>
            
            <!-- Modal Body -->
            <div class="modal-body">
                
                <form:form method="POSt" action="/createReceipt" role="form" modelAttribute="receipt" commandName="receipt" id="insert-receipt-form" enctype="multipart/form-data">
                	<input type="hidden" value="" id="receiptId" name="receiptId"/>
                	<div class="form-group">
						<label class="control-label" for="date">Date</label> 
						<form:input class="form-control" type="date" path="date" id="date" name="date" placeholder="Enter Date DD/MM/YYYY"
							required="required" />
					</div>
					<div class="form-group">
					  	<label for="description">Description</label>
					    <form:input type="text" path="description" class="form-control" id="description" placeholder="Enter a Description" required="required"/>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="category">Category</label> 
						<form:select path="category" id="category" class="form-control" required="required">
							<option value="0" selected>Choose a Category</option>
							<option value="Food">Food</option>
							<option value="Accommodation">Accommodation</option>
							<option value="Health">Health</option>
							<option value="Travel">Travel</option>
							<option value="Leisure">Leisure</option>
							<option value="Transport">Transport</option>
						</form:select>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="cost">Cost</label>
						<div data-role="controlgroup" data-type="horizontal">
							<form:input data-wrapper-class="controlgroup-textinput ui-btn" class="form-control"
								style="width:10%;float:left;vertical-align:bottom;"
								path="preCost" id="preCost" placeholder="Enter Cost $0.00" required="required" />
							<form:select path="dollarType" style="width:auto;float:left;vertical-align:bottom;margin-left:5px;" id="dollarType" class="form-control cd-select" required="required">
								<option value="NZD" selected>NZD</option>
								<option value="AUD">AUD</option>
								<option value="USD">USD</option>
							</form:select>
							<form:input id="cost" path="cost" readonly="true" class="form-control" size="4" style="width:10%;display:none;"/>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="form-group">
						<label class="control-label" for="claim">Claimable</label> 
						<form:select path="claim" id="claim" class="form-control" required="required">
							<option class="dis" disabled selected value="">Choose a Yes/No</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
							<option value="Maybe">Maybe</option>
						</form:select>
					</div>
					
					<div class="form-group">
						<label class="control-label" for="uploadFile">File input</label>
						<form enctype="multipart/form-data">
							<input type="file" multiple="true" class="form-control" name="uploadFile" id="uploadFile" onchange="verifyPic();" title="choose a receipt picture" />
						</form>
						<div id="progressDiv" style="TEXT-ALIGN: center;display:none">
							Please wait for the upload to complete...
							<progress></progress>
						</div>
						<div id="result" style="padding-top: 4px;"></div>
						<img id="uploadedImage" name="uploadedImage" style="padding-top: 4px; width: 80%;float:left;vertical-align:bottom;" class="img-responsive" alt="No uploaded picture" src="">
						<img alt="" name="removeImage" title="Remove the uploaded image" src="<%=request.getContextPath()%>/assets/img/receipt/remove.png" style="float:left;vertical-align:bottom;margin-left:5px;width:20px;height: 20px;cursor:pointer" id="removeFile">
						<form:hidden path="fileName" id="fileName" />
					</div>
                </form:form>
                
            </div>
            
            <div class="clearfix"></div>
            
            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">
                            Close
                </button>
                <button type="button" data-url="<%=request.getContextPath()%>/saveReceipt" id="saveReceipt" class="btn btn-primary">
                    Save changes
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="receipt-modal-label" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="receipt-modal-label">Confirm Delete</h4>
            </div>
        
            <div class="modal-body">
                <p>You are about to delete one track, this procedure is irreversible.</p>
                <p>Do you want to proceed?</p>
                <p class="debug-url"></p>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>
 
<div id="showImage" title="Receipt Image" style="display:none"></div>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/js/receiptList.js"></script>
</body>
</html>
