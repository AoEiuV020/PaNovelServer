<?php
/**
 * Created by AoEiuV020 on 2018.06.22-10:07:11.
 */

class Novel
{
    public $id;
    public $site;
    public $author;
    public $name;
    public $detail;
    public $chaptersCount;
    public $receiveUpdateTime;
    public $checkUpdateTime; // 2018-06-19 18:03:54

    public function __construct(array $array = null)
    {
        if ($array) {
            foreach ($this as $field => &$value) {
                $value = $array[$field] ?? $array[humpToLine($field)] ?? null;
            }
        }
    }

    public function bookId()
    {
        return "$this->name.$this->author.$this->site";
    }

}