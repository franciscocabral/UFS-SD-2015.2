<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <link rel="stylesheet" href="/css/login.css">
    <meta charset="UTF-8" />
    <title>Fofocas - Cadastro</title>
  </head>
  <body>
  	<h1>
	<div class="login-page">
  <div class="form">
<form class="login-form" action="signIn_test.php">
      <input type="text" placeholder="Nome"  name="name"/>
      <input type="password" placeholder="Senha" name="password1"/>
	  <input type="password" placeholder="Repita a senha" name="password2"/>
      <input type="text" placeholder="Email" name="email"/>
      <button>Registrar</button>
      <p class="message">Já possui uma conta? <a href="<?= Yii::$app->urlManager->createUrl(["site/login"]); ?>">Logar</a></p>
    </form>
	</div>
	</div>
	</h1>

  </body>
</html>
