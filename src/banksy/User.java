package banksy;



public class User{

public boolean createUser(){
  return false;
}

private boolean nameCheck(){
  return false;
}

private boolean passwordCheck(){
  return false;
}

private boolean emailCheck(){
  return false;
}

private boolean ssnCheck(){
  return false;
}

private boolean checksPassed(){
  return(createUser()&&nameCheck()&&passwordCheck()&&emailCheck()&&ssnCheck());
}
}
