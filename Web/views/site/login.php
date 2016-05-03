<!DOCTYPE html>
<html lang="pt-br">
  <head>
	<link rel="stylesheet" href="/css/login.css">
    <meta charset="UTF-8" />
    <title>Fofocas</title>
  </head>
  <body>
  	<h1>
<div class="login-page">
  <div class="form">
    <form class="login-form" action="login_test.php">
      <input type="text" placeholder="Email" name="email"/>
      <input type="password" placeholder="Senha"/>
      <button>Entrar</button>
      <p class="message">Não possui uma conta? <a href="<?= Yii::$app->urlManager->createUrl(["site/signin"]); ?>">Registrar</a></p>
    </form>
  </div>
</div>
	</h1>

  </body>
</html>
