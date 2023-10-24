import terser from '@rollup/plugin-terser';

export default [
    {
        input: './dist/fesm2022/fujion-angular-bootstrap.mjs',
        output: [
            {
                file: '${webjar.target}/dist/fujion-angular-bootstrap.umd.js',
                format: 'umd',
                name: 'ng.bundle'
            },
            {
                file: '${webjar.target}/dist/fujion-angular-bootstrap.umd.min.js',
                format: 'umd',
                name: 'ng.bundle',
                plugins: [terser()]
            }
        ]
    }
];
