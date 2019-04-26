<?php
$conn= mysqli_connect("localhost","root","");
mysqli_select_db($conn,"Bank");

//Login
if (isset($_REQUEST['ID']) && isset($_REQUEST['password']) &&
  empty($_REQUEST['Query']) && empty($_REQUEST['Deposit']) &&
  empty($_REQUEST['Withdraw'])&& empty($_REQUEST['History']) &&
  empty($_REQUEST['cooki'])) {

    $id=$_REQUEST['ID'];
    $pass=$_REQUEST['password'];
    $sql="SELECT  password FROM users where ID=".$id;
    $result= $conn->query($sql);
    $row=$result->fetch_assoc();
    if($row['password']==$pass) echo "True Password";
    else echo "Wrong Password";
}

if (isset($_REQUEST['cooki'])) {
    $cookieName=$_REQUEST['cooki'];
    $cookieVal="";
    $timeframe = 90 * 60 * 24 * 60 + time();
    if (isset($_COOKIE[$cookieName])) {
        $visit = $_COOKIE[$cookieName];
        setcookie($cookieName, date("G:i - m/d/y"), $timeframe);
        echo "Your have visited last time at- ". $visit;
    } else {
        echo "Welcome to our web page!";
        setcookie($cookieName, date("G:i - m/d/y"), $timeframe);
    }
}

if (isset($_REQUEST['Deposit']) && isset($_REQUEST['ID'])) {
    $id = $_REQUEST['ID'];
	  $val = $_REQUEST['Deposit'];
    $sql = "SELECT balance FROM users where ID=".$id;
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    $balance = $row['balance'];
    $balance += $_REQUEST['Deposit'];

    $sql = "UPDATE users SET balance=".$balance." where ID=".$id;
    $result = $conn->query($sql);

	  $sql = "INSERT INTO transactions(Type,Value,user_id) values('Deposit',".$val.",".$id.")";
    $result = $conn->query($sql);
}

if (isset($_REQUEST['Withdraw']) && isset($_REQUEST['ID'])) {
    $id = $_REQUEST['ID'];
    $sql = "SELECT balance FROM users where ID=".$id;
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    $balance = $row['balance'];
	  $val = $_REQUEST['Withdraw'];
    $balance -= $_REQUEST['Withdraw'];
    if ($balance<0) {
      echo"Not Enough";
    } else {
      $sql = "UPDATE users SET balance=".$balance." where ID=".$id;
      $result = $conn->query($sql);
  	  $sql = "INSERT INTO transactions(Type,Value,user_id) values('Withdraw',".$val.",".$id.")";
      $result = $conn->query($sql);
    }
}

if (isset($_REQUEST['Query']) && isset($_REQUEST['ID'])) {
    $id = $_REQUEST['ID'];
    $num = (int) $_REQUEST['Query'];
    $sql = "select * from transactions where user_id=".$id." order by id desc";
    $result= $conn->query($sql);
    $str = "";

    if ($result->num_rows > 0) {
		    while($row = $result->fetch_assoc()) {
			       if($num == 0) {
               break;
             }
             echo $row["Type"]. ";" . $row["Value"]. ";" . $row["Date"] . "\n";
			       $num -= 1;
		    }
    } else {
      echo "No Recent transactions";
    }

}

if (isset($_REQUEST['History']) && isset($_REQUEST['ID'])) {
    $id = $_REQUEST['ID'];
	  $sql = "select * from transactions where user_id=".$id;
    $result = $conn->query($sql);
  	$str = "";

	  if ($result->num_rows > 0){
		    while($row = $result->fetch_assoc()) {
			       echo $row["Type"]. ";" . $row["Value"]. ";" . $row["Date"] . "\n";
		    }
    } else {
      echo "No Recent transactions";
    }
}
