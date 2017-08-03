/**
 * @author zhangrp
 * Created on 2015/12/7 17:19
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

var gulp = require('gulp');
var concat = require("gulp-concat");

//aiui合并
gulp.task('aiui-concat', function () {
    //gulp.src(['./resources/frame/aiui/*.js', '!./resources/frame/aiui/aiui.all.js'])
    gulp.src(['./resources/frame/aiui/aiui.address.js',
        './resources/frame/aiui/aiui.dyncinput.js',
        './resources/frame/aiui/aiui.autocomplete.js',
        './resources/frame/aiui/aiui.bcerinput.js',
        './resources/frame/aiui/aiui.button.js',
        './resources/frame/aiui/aiui.calendar.js',
        './resources/frame/aiui/aiui.combobox.js',
        './resources/frame/aiui/aiui.tilecombobox.js',
        './resources/frame/aiui/aiui.datagrid.js',
        './resources/frame/aiui/aiui.datebox.js',
        './resources/frame/aiui/aiui.form.js',
        './resources/frame/aiui/aiui.tabstrip.js',
        './resources/frame/aiui/aiui.textbox.js',
        './resources/frame/aiui/aiui.tree.js',
        './resources/frame/aiui/aiui.ui.js',
        './resources/frame/aiui/aiui.window.js',
        './resources/frame/aiui/aiui.loader.js'])
        .pipe(concat('aiui.all.js'))
        .pipe(gulp.dest('./resources/frame/aiui/'));
});

//任务汇总
gulp.task('default', [], function () {
    gulp.start(
        'aiui-concat'
    );
});