<?php
/**
 * Created by AoEiuV020 on 2018.06.26-15:17:34.
 */

require_once __DIR__ . '/env.php';
require_once __DIR__ . '/model/Novel.php';
require_once __DIR__ . '/delete.php';
require_once __DIR__ . '/connect.php';

function tagMd5(Novel $novel)
{
    $str = "{$novel->site}.{$novel->author}.{$novel->name}";
    $tag = md5($str);
    return $tag;
}

function pushUpdate(Novel $novel)
{
    ini_set('display_errors', 'on');
    global $jc;
    $client = new JPush\Client($jc->appKey, $jc->masterSecret);
    $tag = tagMd5($novel);
    $msg = array();
    $msg['novel'] = json_encode($novel);
    $payLoad = $client->push()
        ->setPlatform('android')
        ->addAlias('debug')
        ->addTag($tag)
        ->setMessage('msgContent', null, null, $msg);
    try {
        $response = $payLoad->send();
        logd('jpush response: ' . json_encode($response));
    } catch (\JPush\Exceptions\APIConnectionException $e) {
        logd('jpush connect error: ' . $e->getMessage());
    } catch (\JPush\Exceptions\APIRequestException $e) {
        logd('jpush request error: ' . $e->getMessage());
        if ($e->getCode() == 1011) {
            // 如果没人订阅，
            global $con;
            deleteNovel($con, $novel);
        }
    }
}