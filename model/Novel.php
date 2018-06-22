<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:07:11.
 */

// 没找到方便的json反序列化，
class Novel
{
    public $id;
    public $site;
    public $author;
    public $name;
    public $detail;
    public $chapters_count;
    public $receive_update_time;
    public $check_update_time; // 2018-06-19 18:03:54

    public function __construct(array $array = null)
    {
        if ($array) {
            foreach ($this as $field => &$value) {
                $value = $array[$field] ?? null;
            }
        }
    }

    public function bookId()
    {
        return "$this->name.$this->author.$this->site";
    }
}