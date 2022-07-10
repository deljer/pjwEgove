
$(document).ready(function(){
	//1.공통 초기화 함수
	comInit();
	
	
	
	
})



/********************************************************
 * @function : comInit
 * @param    : X  
 * @return   : X
 * @etc      : 공통 초기화 함수   
 ***********************************************************/
const comInit =async function(){
	//사이드 메뉴 가져오기
	let menuData = await comAjax('/selectMenuInfo.do');
	await sideMenuViewMake(menuData);
//	$.getScript('/static/js/lib/main.js');
	window.main();
	
	
	
}



/********************************************************
 * @function : comAjax 
 * @param    : url  = 호출 url 
 * @param    : requestData  = 전달할 데이터 
 * @param    : callback_(defalut= defalutSuccess) = 성공 callback function
 * @param    : errorCalback_(defalut = defalutError) = 에러 callback function
 * @param    : option_ = 옵션 
 * @return   : await - data   none-await ajax data
 * @etc      : ajax 펑션    
 ***********************************************************/
const comAjax =function(url,requestData,option_,callback_=defalutSuccess,errorCalback_=defalutError ){
	option ={
			type 	    : 'post',
			dataType    : 'json',
			contentType : 'application/json',
			beforeSend  : (a,b)=>{},
			crossDomain : false,
	};
	option        = $.extend(option, option_);
	if(option.contentType =='application/json'){
		option.data   = JSON.stringify(requestData);	
	}else{
		option.data = requestData
	}
	
	
	 
	return $.ajax(url,option).then(callback_,errorCalback_);
};

const defalutSuccess = function(rsData,stat,response) {
	return {resultCode:response.status,
		resultStat:stat,	
		resultData:rsData};
};

const defalutError = function(error){
alert(
`error ==${error.statusText}
errorCode ==${error.status}`);
};

/********************************************************
 * @function : pageMove
 * @param    : url = 이동 url  
 * @return   : 
 * @etc      : 화면 이동  utill function  
 ***********************************************************/
const pageMove = function(url_){
	$(innerDiv).find('section').remove();
	
	if(url_=='/'){
		window.location.href='/'
	}else{
		$("#innerDiv").load("pageMove.do", { url : url_});
	}
	

}


/********************************************************
 * @function : sideMenuViewMake
 * @param    : menuDataList : 메뉴데이터  
 * @return   : 
 * @etc      : 사이드 메뉴 구성 function 
 ***********************************************************/
const sideMenuViewMake  = function(menuDataList){
	let returnValue = false;
	let menuData = menuDataList.resultData.resultItem;
	
	for(let menuItem of menuData){
		if(menuItem.menuId != '000000' && menuItem.menuUpperId== '000000'){
			let findId = [menuItem];
			let menuSet = [];
			menuSet.push(menuItem);
			while(menuItem.menuGb != '01'){
				let filteringItem = menuData.filter(e=> findId.some(findIdNm=>findIdNm.menuId == e.menuUpperId));
				
				if(filteringItem.length>0){
					menuSet.push(...filteringItem);
					findId  = [...filteringItem];
				} 
				else{
					break;
				}
			}
			menuViewMake(menuSet);
		}
	}
	return true;
};


/********************************************************
 * @function : menuViewMake
 * @param    : dataArray_
 * @return   : 
 * @etc      : 메뉴 화면 구성 2단메뉴까지만 허용 3단 미허용 기능구현중 퍼블리싱 영역이 햇갈려 포기   
 ***********************************************************/
const menuViewMake = function(dataArray_){
	
	let firstMenu = dataArray_.find(e=>e.menuLevel=='1')
	let menuItem = null;

	let folder_Str = '<li> <span class="opener"></span> <ul> </ul> </li>';
	let menu_Str   = '<li><a type="button"> </a></li>';

	
	if(firstMenu.menuGb =='01'){
		menuItem = $(menu_Str);
		menuItem.find('a').text(firstMenu.menuNm);
		menuItem.find('a').on('click',e=>pageMove(firstMenu.menuUrl));
		menuItem.find('a').data(firstMenu);
		
	}else{
		//폴더 구성
		menuItem = $(folder_Str);
		menuItem.find('span').text(firstMenu.menuNm);
		menuItem.find('span').data(firstMenu);
		//아이템 구성
		let subMenuItemList = dataArray_.filter(e=>firstMenu.menuId != e.menuId);
		subMenuItemList = subMenuItemList.sort((a,b)=> a.menuOrder- b.menuOrder);
		subMenuItemList.forEach((data,index)=>{
			let subMenuItem  = $(menu_Str);
			subMenuItem.find('a').text(data.menuNm);
			subMenuItem.find('a').on('click',e=>pageMove(data.menuUrl));
			subMenuItem.find('a').data(data);
			menuItem.find('ul').append(subMenuItem);
		})
		
	}
	$('#menuNavItem').append(menuItem);
}

/********************************************************
 * @function : tableItemViewMake
 * @param    : dataArray_
 * @return   : 
 * @etc      : 테이블 데이터 반영    
 ***********************************************************/
const tableItemViewMake = function(dataArray_){

}


/********************************************************
 * @function : fileUploadChange
 * @param    : 
 * @return   : 
 * @etc      : 파일 업로드 onchange
 ***********************************************************/
const fileUploadChange  = function(e){
	let fileUploadStr = "";
	fileUploadStr += '<div class="row gtr-uniform">';
	fileUploadStr += '	<div  class="col-4 col-12-xsmall">';
	fileUploadStr += '		<input type="text" name="path" value=""  readonly="readonly"  placeholder="Name" />';
	fileUploadStr += '	</div>';
	fileUploadStr += '	<div name="upItemDiv" class="col-4 col-12-xsmall" >';
	fileUploadStr += '		<label class="button fileDelete">삭제</label>';
	fileUploadStr += '	</div>';
	fileUploadStr += '</div>';		
	let fileItem = $(fileUploadStr);
	let cloneFile = e.cloneNode();
	cloneFile.id='';
	
	fileItem.find('[name="upItemDiv"]').append(cloneFile);
	fileItem.find('[name="path"]').val(e.value);
	fileItem.find('.fileDelete').on('click',fileDelete);
	
	e.value = '';
	
	
	$('#fileUploadTarget').append(fileItem);
}

/********************************************************
 * @function : fileDelete
 * @param    : 
 * @return   : 
 * @etc      : 파일 삭제 evenet
 ***********************************************************/
const fileDelete  = function(event){
	let uploadFileList = $('#fileUploadTarget').children().get();
	uploadFileList.forEach(item=> {
		if($(item).find(event.target).length>0){
			$(item).remove();
		}
	});
}

/********************************************************
 * @function : uploadToServer
 * @param    : 
 * @return   : 
 * @etc      : 파일 업로드 evenet
 ***********************************************************/
const uploadToServer  = function(e){
	let data = {param:'1',param2:'2','param3':'3'};
	
	Object.keys(data).forEach(function(param){
		let temp = document.createElement('input');
		temp.type='text';
		temp.name=param;
		temp.classList.add('tempData');
		temp.style.display='none';
		$('#fileUploadTarget')[0].append(temp);
	})
	let formData = new FormData($('#fileUploadTarget')[0]);
	let fileItem = $('#fileUploadTarget').find('[name="uploadFile"]').get();
	$('.tempData').remove();
	//fileItem.forEach(item=>{
	//	formData.append("item",item.files[0]);
	//})
	comAjax('/fileUpload.do',formData,{
		processData: false,
	    contentType: false
	},function(data){
		
		
	})
}

