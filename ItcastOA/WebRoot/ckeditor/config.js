/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//预览区域显示内容
	config.image_previewText=' ';  
	//定义图片上传的处理action,前端界面会根据该字段是否赋值自动加载该上传表单
    config.filebrowserImageUploadUrl= "imgUploadAction.action"; //要上传的action或servlet   
};
