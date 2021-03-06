<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<section>
	<h2>파일 업로드/다운로드 </h2>
	<hr/>
	<h4>사용 기술</h4>
	<ul>
		<li>javaScript.</li>
		<li>java</li>
		<li>jquery</li>
	</ul>

	<h4>구현</h4>
<div id="uploadForm">
	<div class="row gtr-uniform">
		<div class="col-4 col-12-xsmall">
			<input type="text" name="demo-name" id="file-route" value="" readonly="readonly"  placeholder="Name" />
		</div>
		<div class="col-4 col-12-xsmall" >
			<label for="fileUpload" class="button primary">업로드 파일 추가 </label>
			<input id="fileUpload" name="uploadFile" style="display:none" type="file" multiple="multiple" onchange="fileUploadChange(this)">
		</div>
	</div>
	<form id="fileUploadTarget" enctype="multipart/form-data">
	</form>
	<label id="fileUploadServer" onclick="uploadToServer(this)" class="button primary">파일 업로드</label>
</div>

	<h4>파일 목록 및 다운로드</h4>
		<div style="text-align: center;" class="table-wrapper">
			<table class="alt">
				<thead>
					<tr>
						<th>파일명</th>
						<th>파일 위치&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </th>
						<th>다운로드</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div style="text-align: center;">
			<ul class="pagination">
				<li><span class="button disabled">Prev</span></li>
				<li><a href="#" class="page active">1</a></li>
				<li><a href="#" class="page">2</a></li>
				<li><a href="#" class="page">3</a></li>
				<li><span>&hellip;</span></li>
				<li><a href="#" class="page">8</a></li>
				<li><a href="#" class="page">9</a></li>
				<li><a href="#" class="page">10</a></li>
				<li><a href="#" class="button">Next</a></li>
			</ul>
		</div>
		
<script type="text/javascript">
$(document).ready(e=>{
	selectUploadFile();
})

</script>

</section>
