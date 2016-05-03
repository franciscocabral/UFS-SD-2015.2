<?php

use yii\db\Migration;

class m160503_193712_init extends Migration{


    // Use safeUp/safeDown to run migration code within a transaction
    public function safeUp(){
      $this->createTable('usuario', [
        'id' => $this->primaryKey(),
        'nome' => $this->string()->notNull(),
        'email' => $this->string()->notNull(),
        'senha' => $this->string()->notNull(),
      ]);

      $this->createTable('contato', [
        'id' => $this->primaryKey(),
        'usuario_fk' => $this->integer()->notNull(),
        'contato_fk' => $this->integer()->notNull(),
      ]);

      $this->createTable('mensagem', [
        'id' => $this->primaryKey(),
        'de_fk' => $this->integer()->notNull(),
        'para_fk' => $this->integer()->notNull(),
        'mensagem' => $this->string()->notNull(),
        'hora' => $this->dateTime()->defaultValue(date("Y-m-d H:i:s")),
      ]);

      $this->addForeignKey(
        'fk-contatos-usuario',
        'contato',
        'usuario_fk',
        'usuario',
        'id',
        'CASCADE'
      );

      $this->addForeignKey(
        'fk-contatos-contato',
        'contato',
        'contato_fk',
        'usuario',
        'id',
        'CASCADE'
      );
      $this->addForeignKey(
        'fk-mensagem-de',
        'mensagem',
        'de_fk',
        'usuario',
        'id',
        'CASCADE'
      );

      $this->addForeignKey(
        'fk-mensagem-para',
        'mensagem',
        'para_fk',
        'usuario',
        'id',
        'CASCADE'
      );
    }

    public function safeDown(){
      $this->dropTable('usuario');
      $this->dropTable('contato');
      $this->dropTable('mensagem');
    }
}
