'use strict';
var fallback = require('connect-history-api-fallback');
var log = require('connect-logger');
var cors = require('cors');

module.exports = {
    injectChanges: false, // workaround for Angular 2 styleUrls loading
    files: ['./**/*.{html,htm,css,js}'],
    watchOptions: {
        ignored: 'node_modules'
    },
    server: {
        baseDir: 'build',
        middleware: [
            log({format: '%date %status %method %url'}),
            cors(),
            fallback({
                index: '/index.html',
                disableDotRule: true,
                htmlAcceptHeaders: ['text/html', 'application/xhtml+xml']
            })
        ]
    }
};