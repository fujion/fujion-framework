import terser from '@rollup/plugin-terser';

export default [
    {
        input: './dist/fesm2022/fujion-angular-example.mjs',
        output: [
            {
                file: `${webjar.target}/dist/fujion-angular-example.umd.js`,
                format: 'umd',
                name: 'ng.angular.example'
            },
            {
                file: `${webjar.target}/dist/fujion-angular-example.umd.min.js`,
                format: 'umd',
                name: 'ng.angular.example',
                plugins: [terser()]
            }
        ]
    }
];
