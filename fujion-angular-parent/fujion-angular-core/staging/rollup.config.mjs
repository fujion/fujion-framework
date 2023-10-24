import terser from '@rollup/plugin-terser';

export default [
    {
        input: './dist/fesm2022/fujion-angular-bootstrap.mjs',
        output: [
            {
                file: '${webjar.target}/dist/fujion-angular-bootstrap.umd.js',
                format: 'umd',
                name: 'ng.angular.bootstrap'
            },
            {
                file: '${webjar.target}/dist/fujion-angular-bootstrap.umd.min.js',
                format: 'umd',
                name: 'ng.angular.bootstrap',
                plugins: [terser()]
            }
        ]
    },
    {
        input: './js/fujion-angular-core.js',
        output: [
            {
                file: '${webjar.target}/dist/fujion-angular-core.js',
                format: 'iife',
                name: 'ng.angular.core'
            },
            {
                file: '${webjar.target}/dist/fujion-angular-core.min.js',
                format: 'iife',
                name: 'ng.angular.core',
                plugins: [terser()]
            }
        ]
    }
];
