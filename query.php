<pre>
<?php
/**
 * Created by AoEiuV020 on 2018.06.21-22:52:50.
 */
$con = new mysqli("localhost", "panovel", "panovel", "panovel");
$con->set_charset("utf-8");
if ($con->connect_error) {
    die('connect error: ' . $con->connect_error);
}
$con->query('set names utf8');
$result = $con->query("select * from novel");
while ($row = $result->fetch_assoc()) {
    print_r($row);
}
$con->close();
?>
</pre>
