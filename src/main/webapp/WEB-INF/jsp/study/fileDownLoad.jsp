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
<form id="uploadForm">
	<div class="row gtr-uniform">
		<div class="col-4 col-12-xsmall">
			<input type="text" name="demo-name" id="file-route" value="" readonly="readonly"  placeholder="Name" />
		</div>
		<div class="col-4 col-12-xsmall" >
			<label for="fileUpload" class="button primary">파일 업로드</label>
			<input id="fileUpload" style="display:none" type="file" onchange="fileUploadChange(this)">
		</div>
	</div>
	<div id="fileUploadTarget">
	
	</div>
</form>
</section>

