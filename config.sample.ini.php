<?php
/**
 * Created by AoEiuV020 on 2018.06.22-09:23:10.
 */

$ds->host = 'localhost';
$ds->username = 'panovel';
$ds->password = 'panovel';
$ds->database = 'panovel';
// utf8不能写成utf-8,
$ds->charset = 'utf8';

ini_set('display_errors', 'On');
ini_set('html_errors', 0);

// ----------------------------------------------------------------------------------------------------
// - Error Reporting
// ----------------------------------------------------------------------------------------------------
error_reporting(-1);

// ----------------------------------------------------------------------------------------------------
// - Shutdown Handler
// ----------------------------------------------------------------------------------------------------
function ShutdownHandler()
{
    if (@is_array($error = @error_get_last())) {
        return (@call_user_func_array('ErrorHandler', $error));
    };

    return (TRUE);
}

;

register_shutdown_function('ShutdownHandler');
