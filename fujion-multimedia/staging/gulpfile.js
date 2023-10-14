const {src, dest, series} = require('gulp');
const minifyJS = require('gulp-minify');
const minifyCSS = require('gulp-clean-css');
const rename = require('gulp-rename');
const print = require('gulp-print').default;

const srcDir = '${webjar.target}/';
const destDir = srcDir;

function task1() {
    return _minifyJS('js/*.js', 'js')
}

function task2() {
    return _minifyCSS('css/*.css', 'css')
}

function _toSrc(_src) {
    return src(_src, {allowEmpty: true, cwd: srcDir});
}

function _toDest(_dest) {
    return dest(_dest || '.', {cwd: destDir});
}

function _minifyJS(_src, _dest) {
    console.log('  Minifying ' + _src);
    return _toSrc(_src)
        .pipe(print())
        .pipe(minifyJS(
            {
                noSource: true,
                ext: {
                    min: '.min.js'
                }
            }))
        .pipe(_toDest(_dest))
}

function _minifyCSS(_src, _dest) {
    console.log('  Minifying ' + _src);
    return _toSrc(_src)
        .pipe(print())
        .pipe(rename(path => path.extname = '.min.css'))
        .pipe(minifyCSS())
        .pipe(_toDest(_dest))
}

exports.default = series(task1, task2);
