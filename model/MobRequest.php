<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:10:05.
 */

// 没什么用，没法反序列化，
class MobRequest
{
    public $data;

    function __construct($data)
    {
        $this->data = $data;
    }
}