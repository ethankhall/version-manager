"use strict";

const tsc = require("gulp-typescript");
const gulp = require("gulp");
const del = require("del");
const sourcemaps = require('gulp-sourcemaps');
const tsProject = tsc.createProject("tsconfig.json");
const reporter = tsc.reporter;
var concat = require('gulp-concat');
var minify = require('gulp-minify');
var sysBuilder = require('systemjs-builder');
var connect = require('gulp-connect');
var runSequence = require('run-sequence');


/**
 * Remove build directory.
 */
gulp.task('clean', function (cb) {
    return del(["build"], cb);
});

/**
 * Compile TypeScript sources and create sourcemaps in build directory.
 */
gulp.task("compile", function () {
    return gulp.src("app/**/*.ts")
        .pipe(sourcemaps.init())
        .pipe(tsProject(reporter))
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest("build"));
});

gulp.task('bundle:js:raw', function () {
    var builder = new sysBuilder('build', './systemjs.config.js');
    return builder.buildStatic('main.js', 'public/js/app.js', {minify: false, encodeNames: false});
});

gulp.task('bundle:js', function (cb) {
    runSequence('compile', 'libs', 'bundle:libs', 'bundle:js:raw', cb);
});

/**
 * Copy all resources that are not TypeScript files into build directory.
 */
gulp.task("bundle:resources", function () {
    return gulp.src(["app/**/*.html", "app/*.html", "index.html", "node_modules/bootstrap/dist/css/bootstrap.min.css", "styles.css"])
        .pipe(gulp.dest("public"));
});

gulp.task('bundle:libs', function () {
    return gulp.src([
        'node_modules/core-js/client/shim.min.js',
        'node_modules/zone.js/dist/zone.js',
        'node_modules/reflect-metadata/Reflect.js',
        'node_modules/systemjs/dist/system.src.js',
        'systemjs.config.js'
    ])
    // .pipe(minify())
        .pipe(concat('vendors.js'))
        .pipe(gulp.dest('public/js'));
});

gulp.task('bundle', ['bundle:js', 'bundle:resources', 'bundle:libs']);

/**
 * Copy all required libraries into build directory.
 */
gulp.task("libs", function () {
    return gulp.src([
        'core-js/client/shim.min.js',
        'systemjs/dist/system-polyfills.js',
        'systemjs/dist/system.src.js',
        'reflect-metadata/Reflect.js',
        'angular2-cookie/**',
        'bootstrap/dist/css/bootstrap.min.css',
        'rxjs/**/*.js',
        'zone.js/dist/**',
        '@angular/**/bundles/**'
    ], {cwd: "node_modules/**"}) /* Glob required here. */
        .pipe(gulp.dest("build/lib"));
});

/**
 * Watch for changes in TypeScript, HTML and CSS files.
 */
gulp.task('watch', function () {
    gulp.watch(["app/**/*.ts"], ['compile']);
    gulp.watch(["build/**/*.js"], ['bundle:js:raw']);
    gulp.watch(["./app/**/*.html", "./app/**/*.css", "index.html", "styles.css"], ['bundle:resources']);
});

/**
 * Build the project.
 */
gulp.task("build", ['compile', 'bundle'], function () {
    console.log("Building the project ...");
});

gulp.task('serve', function() {
    connect.server({
        root: 'public',
        port: 3000,
        fallback: 'index.html'
    });
});