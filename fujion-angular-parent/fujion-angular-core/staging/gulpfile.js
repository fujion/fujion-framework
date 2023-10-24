const {src, dest, series} = require('gulp');
const minifyJS = require('gulp-minify');
const minifyCSS = require('gulp-clean-css');
const rename = require('gulp-rename');
const print = require('gulp-print').default;

const srcDir = '${webjar.staging}/';
const destDir = '${webjar.target}/';

function task1() {
    return _minifyJS('js/*.js', 'dist');
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
        .pipe(_toDest(_dest))
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

exports.default = task1;
