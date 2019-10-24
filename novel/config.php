<?php
/**
 * Created by AoEiuV020 on 2018.09.04-19:31:54.
 */

require_once __DIR__ . '/../env.php';
require_once __DIR__ . '/../model/Config.php';

$config = new Config();
$config->apiUrl = 'http://host810254071.s354.pppf.com.cn';
$config->minVersion = '3.3.3';
$config->qqGroup = '206522738';
$config->redPacket = 'https://qr.alipay.com/c1x06390qprcokz0xvccv13';
success($config);
