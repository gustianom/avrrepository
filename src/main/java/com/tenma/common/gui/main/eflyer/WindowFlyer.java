package com.tenma.common.gui.main.eflyer;

import com.tenma.common.gui.display.TenmaWindow;
import com.tenma.common.util.Constants;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;


/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/16/2015
 * Time    : 2:46 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.announcement
 */
public class WindowFlyer extends TenmaWindow implements Button.ClickListener {
    private Button btnSave;
    private Button btnBack;
    private IFlyer master;
    private CKEditorTextField ckEditorTextField;

    public WindowFlyer(IFlyer master) {
        super();
        this.master = master;
        this.setCaption(param.getLabel("change.text"));
        createUI();
    }

    private void createUI() {
        VerticalLayout vl = new VerticalLayout();
        CKEditorConfig config = new CKEditorConfig();
        config.useCompactTags();
        config.disableElementsPath();
        config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
        config.disableSpellChecker();
        config.setToolbarCanCollapse(false);
        config.setWidth("100%");
//        config.disableResizeEditor();
        StringBuffer sbfConfig = new StringBuffer()
                .append("{ name: 'clipboard',items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ]},")
                .append("{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ]},")
                .append("{ name: 'paragraph',items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ]},")
                .append("{ name: 'styles', items: [ 'Font','FontSize' ] },")
                .append("{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }");
        config.addCustomToolbarLine(sbfConfig.toString());
//                "{ items : ['Undo','Redo','-','Bold','Italic','-','SpecialChar','-','BulletedList','NumberedList','Format'] }"
//
//        );
        config.addToRemovePlugins("scayt");
        ckEditorTextField = new CKEditorTextField(config, master.getDataContent());
        vl.addComponent(ckEditorTextField);

        btnSave = new Button(param.getLabel(Constants.LABEL_SAVE), this);
        btnBack = new Button(param.getLabel(Constants.LABEL_BACK), this);
        btnSave.setIcon(new ThemeResource(Constants.SAVE_ICON));
        btnBack.setIcon(new ThemeResource(Constants.BACK_ICON));
        HorizontalLayout hl = new HorizontalLayout(btnSave, btnBack);
        hl.setSpacing(true);
        vl.addComponent(hl);
        vl.setSpacing(true);
        setContent(vl);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton().equals(btnSave)) {
            master.setDataContent(ckEditorTextField.getValue());
            getUI().removeWindow(this);
        } else if (clickEvent.getButton().equals(btnBack)) {
            getUI().removeWindow(this);
        }
    }
}


//        CKEditor ckEditor = new CKEditor();
////        FileResource fl = new FileResource(new File("layouts/images/12/close.jpg"));
//        Image img = new Image("HEHEHEMAN",new ThemeResource("layouts/images/sandal.jpg"));
//        vl.addComponent(img);
//        setContent(vl);

//        Window mainWindow = new Window("Vaadin CKEditor Application", new VerticalLayout());
//        mainWindow.setSizeFull();
//        setMainWindow(mainWindow);

//        mainWindow.addComponent(new Button("Hit server"));

//        config.addOpenESignFormsCustomToolbar();
//        final String editorInitialValue =
//                "<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";
//
//        ckEditorTextField.setValue(editorInitialValue);
//ckEditorTextField.setReadOnly(true);
//        ckEditorTextField.addListener(new Property.ValueChangeListener() {
//
//            public void valueChange(Property.ValueChangeEvent event) {
////                getMainWindow().showNotification("CKEditor contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
//            }
//        });


  /* This is the full list as we know it in CKEditor 4.x
    [
    { name: 'document', items : [ 'Source','-','NewPage','Preview','Print','-','Templates' ] },
    { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
    { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
    { name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
    '/',
    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
    { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
    { name: 'links', items : [ 'Link','Unlink','Anchor' ] },
    { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
    '/',
    { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
    { name: 'colors', items : [ 'TextColor','BGColor' ] },
    { name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
    ]

    //from ckedit,share
    config.toolbar = [
	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ] },
	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
	{ name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
	'/',
	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },
	{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
	{ name: 'insert', items: [ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
	'/',
	{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
	{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
	{ name: 'others', items: [ '-' ] },
	{ name: 'about', items: [ 'About' ] }
];     */