# A simple TTRPG based on Byunds story.

## Abaixo está a documentação da API. Endpoints marcados com (P) são públicos e os marcados com (RA) requerem autorização, ou seja, um token JWT válido.

###### (Fiz a documentação no Readme mesmo porque não estou com tempo nem saco pra montar Swagger)

### (P) /auth/register
#### POST
**Corpo da requisição**:
```json lines
{
  "username": "string",
  "password": "string"
}
```
O nome de usuário pode conter qualquer caractere BMP de U+0020 até U+FFFD com exceção dos dígitos. Além disso, a string inteira deve ter entre 3 e 36 caracteres.

Regex Java: **\\A(?!\\d)[\\u0020-\\uFFFD]{3,36}\\z**

Regex JavaScript: **^(?!\\d)[\\u0020-\\uFFFD]{3,36}$**

A senha deve conter entre 8 e 128 caracteres.

**Respostas**

**201 Created**: Se tudo ocorreu com sucesso.  
- **Headers**:  
Content-Type: text/plain
- **Body**:  
Token JWT

**409 Conflict**: Se o usuário já existir.  
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

**400 Bad Request**: Se o nome de usuário ou senha não atenderem às condições especificadas.  
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
{
  "field": "string",
  "rejectedValue": "string",
  "message": "string"
}
```

### (P) /auth/login
#### POST
**Corpo da requisição**:
```json lines
{
  "username": "string",
  "password": "string"
}
```
Mesmas condições de validação do endpoint de registro. Também devolve 400 Bad Request se não atenderem às condições. Outras possíveis respostas estão listadas abaixo.

**Respostas**:

**200 OK**: Se o login tiver êxito.
- **Headers**:  
Content-Type: text/plain
- **Body**:  
Token JWT

**401 Unauthorized**: Se o usuário não existir ou a senha estiver incorreta.
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string",
}
```

### (RA) /sheet/all-options/{type}
#### GET
**Parâmetros da URL**:  
- **type**: O tipo de opções de enum, que podem ser Profession, Race ou Weapon.  

**Respostas**:  
**200 OK**: Retorna um array de strings em json contendo todas as possíveis opções para o tipo do enum, as quais podem ser enviadas para preenchimento de fichas em outras requisições.

**400 Bad Request**: Se o parâmetro da RL ***type*** não for um dos 3 disponíveis listados acima.

### (RA) /sheet
#### POST
Cria uma nova ficha para o usuário.

**Corpo da requisição**:
```json lines
{
  "name": "string",
  "profession": "string"
}
```
O nome do personagem deve ter entre 3 e 36 caracteres, sendo eles apenas letras, maiúsculas e/ou minúsculas, cujos acentos permitidos são apenas agudo, grave, til, circunflexo e trema, além do C cedilha.

Regex Java: **\A[a-zA-ZáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãõñÃÕÑçÇàèìòùÀÈÌÒÙäëïöüÄËÏÖÜ _'-]{3,36}\z**

Regex JavaScript: **^[a-zA-ZáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãõñÃÕÑçÇàèìòùÀÈÌÒÙäëïöüÄËÏÖÜ _\'-]{3,36}$**

A profissão deve ser uma das opções retornadas pela lista em **GET /sheet/all-options/Profession**.

**Respostas**:  
**201 Created**: Se a ficha tiver sido criada com êxito.
- **Headers**:  
Content-Type: application/json  
Location: [...]/sheet/{id}
- **Body**:
```json lines
{
  "id": "string",
  "name": "string",
  "profession": "string",
  "race": "string",
  "weapon": "string",
  "attributes": {
    "healthPoints": int,
    "manaPoints": int,
    "attack": int,
    "defense": int,
    "speed": int,
    "aim": int,
    "dexterity": int,
    "intelligence": int,
    "strength": int,
    "spirit": int,
    "precision": int
  },
  "inventory": {
    "items": [
      {
        "id": "string",
        "name": "string",
        "amount": int,
        "description": "string"
      },
      ...
    ],
    "size": int,
    "capacity": int
  },
  "ring": {
    "id": long,
    "name": "string",
    "description": "string"
  },
  "bracelet": {
    "id": long,
    "name": "string",
    "description": "string"
  },
  "collar": {
    "id": long,
    "name": "string",
    "description": "string"
  },
  "amulet": {
    "id": long,
    "name": "string",
    "description": "string"
  },
  "abilities": {
    "first": {
      "id": long,
      "name": "string",
      "description": "string"
    },
    "second": {
      "id": long,
      "name": "string",
      "description": "string"
    },
    "third": {
      "id": long,
      "name": "string",
      "description": "string"
    }
  }
}
```
O JSON acima é a estrutura básica de uma ficha completa que a API mandará como resposta em certas requisições. No caso da criação de uma nova ficha, porém, "race" e "weapon" serão null, "inventory">"items" será um array vazio, "ring", "bracelet", "collar" e "amulet" serão null e "abilities">"first", "abilities">"second" e "abilities">"third" também serão null. Assim sendo, você pode esperar uma resposta mais ou menos do tipo abaixo:
```json lines
{
  "id": "string",
  "name": "string",
  "profession": "string",
  "race": null,
  "weapon": null,
  "attributes": {
    "healthPoints": int,
    "manaPoints": int,
    "attack": int,
    "defense": int,
    "speed": int,
    "aim": int,
    "dexterity": int,
    "intelligence": int,
    "strength": int,
    "spirit": int,
    "precision": int
  },
  "inventory": {
    "items": [],
    "size": int,
    "capacity": int
  },
  "ring": null,
  "bracelet": null,
  "collar": null,
  "amulet": null,
  "abilities": {
    "first": null,
    "second": null,
    "third": null
  }
}
```

**500 Internal Server Error**: Eu tomei no cu.
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

### (RA) /sheet/{id}
#### GET
Retorna a ficha com o id provido.  
**Parâmetros da URL**:
- **id**: O id da ficha.

**Respostas**:

**200 OK**: Se a ficha for encontrada. O json retornado segue o mesmo formado do enviado pelo endpoint **POST /sheet** logo acima.

**404 Not Found**: Se não for encontrada nenhuma ficha para o id provido. Não tem body.

### (RA) /sheet/all-sheets-abstracts/{username}
#### GET
Retorna os resumos de todas as fichas do usuário provido. O resumo consiste apenas do id, nome, profissão, raça e arma.

**Parâmetros da URL**:
- **username**: O nome de usuário.

**Respostas**:

**200 OK**: Se as fichas forem encontradas com êxito. Se o usuário não tiver nenhuma ficha, uma lista vazia será retornada.
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
[
  {
    "id": "string",
    "name": "string",
    "profession": "string",
    "race": "string",
    "weapon": "string"
  },
  ...
]
```

**404 Not Found**: Se o usuário provido não existir. Não tem body.

### (RA) /sheet/all-sheets/{username}
#### GET
Faz a mesma coisa que **GET /all-sheets-abstracts/{username}**, mas ao invés de retornar o resumo das fichas, a lista contém as fichas em si, seguindo o mesmo esquema de json descrito em **POST /sheet**; retorna **200 OK**. Retorna **404 Not Found** se o usuário não for encontrado.

### (RA) /sheet/update/{id}
#### PATCH
Atualiza campos de uma ficha, com exceção do id, nome e profissão.

**Parâmetros da URL**:
- **id**: O id da ficha.

**Corpo da requisição**:
```json lines
{
  "race": "string",
  "weapon": "string",
  "attributes": {
    "healthPoints": int,
    "manaPoints": int,
    "attack": int,
    "defense": int,
    "speed": int,
    "aim": int,
    "dexterity": int,
    "intelligence": int,
    "strength": int,
    "spirit": int,
    "precision": int
  },
  "inventory": {
    "items": [
      {
        "id": "string" | long,
        "amount": int
      },
      ...
    ]
  },
  "ring": {
    "id": long | null
  },
  "bracelet": {
    "id": long | null
  },
  "collar": {
    "id": long | null
  },
  "amulet": {
    "id": long | null
  },
  "abilities": {
    "first": boolean | null,
    "second": boolean | null,
    "third": boolean | null
  }
}
```
Algumas considerações precisam ser feitas.

Primeiro, observe que os itens do inventário podem ter dois tipos de ids: o UUID em formato de string e um id numérico long (inteiro de 64 bits). Se um long for passado como id, significa que o pedido é para adicionar o item no inventário do jogador; se for UUID em string, então é apenas para atualizar um item que já existe no inventário do jogador. Este jogo tem itens predefinidos, e a base deles (nome e descrição) estão indexados por um long fixo; assim sendo, quando se pede para adicionar esse item específico no inventário, tudo que é necessário é informar a quantidade, e nisso, ele é convertido em um item de inventário, cujo id não é mais long, mas sim um UUID, simbolizando que ele é único e está presente apenas no inventário de um jogador, e é esse UUID em forma de string que é retornado como id dos itens dos inventários durante requisições de **GET /sheet/{id}** e **GET /sheet/all-sheets/{username}**. Se o json contiver um item cujo id é UUID em string e a quantidade pedida para ser atualizada for 0, então é interpretado que todos os itens desse tipo foram consumidos pelo jogador ou tiveram outro fim, resultando na liberação do slot do inventário. Entretanto, se a quantidade também for 0 mas ao invés de um UUID de item existente no inventário, for um long (ou seja, um pedido para adicionar tal item no inventário), um **400 Bad Request** será retornado. Lembre-se também que um inventário não pode conter mais tipos de itens do que sua capacidade.

Segundo, itens como anel, pulseira, colar e amuleto recebem apenas o id long da entidade, que é um pedido para ser alterada, mas é possível deixar esses campos como null, o que resulta na remoção dessas entidades dos respectivos slots. Portanto, se o objetivo for não modificar uma determinada entidade, é necessário enviar novamente o id dessa entidade no campo.

Terceiro, se "race" ou "weapon" forem null, isso significa que é um pedido para não modificar tais campos. Se eles forem definidos como os mesmos valores que já estavam antes, também não serão modificados. Tudo isso significa que uma vez que raça ou arma é definida como não-nula, não é possível mais voltar a ser nula.

Quarto, se "attributes" não for null, significa que é um pedido para alterar os atributos no geral. Caso ele seja null, mas "race" ou "weapon" tiverem sido modificados, significa que é um pedido para resetar os atributos e recalculá-los com os novos valores de raça e/ou arma.

Quinto, se "abilities" não for null, é possível dizer quando um jogador tem ou não tal abilidade. O parâmetro é uma boolean porque as habilidades são fixas e dependem apenas da raça do indivíduo. Um "true" significa apenas que ele tem essa habilidade liberada, enquanto "false" significa bloqueada. Mandar null significa pedir para manter o valor inalterado.

**Respostas**:

**200 OK**: Se as modificações tiverem sido aplicadas com sucesso.
- **Headers**:  
Content-Type: application/json
- **Body**:  
O json da ficha atualizada, nos mesmos moldes do descrito em **POST /sheet**.

**404 Not Found**: Se nenhuma ficha for encontrada para o id provido na URL. Não tem body.

**403 Forbidden**: Se o usuário não for admin ou o dono da ficha.
- **Headers**:  
  Content-Type: application/json
- **Body**:  
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

**500 Internal Server Error**: Deu ruim.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

### (RA) /sheet/{id}
#### DELETE
Deleta a ficha do id provido.

**Parâmetros da URL**:
- **id**: O id da ficha.

**Respostas**:

**204 No Content**: Se a deleção tiver êxito.

**404 Not Found**: Se nenhuma ficha for encontrada para o id provido na URL. Não tem body.

**403 Forbidden**: Se o usuário não for admin ou o dono da ficha.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

**500 Internal Server Error**: Fudeu a porra toda.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

### (RA) /users
#### DELETE
Deleta a própria conta junto com todas as fichas associadas.

**204 No Content**: Se a deleção tiver êxito.

**403 Forbidden**: Se o usuário for admin.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

### (RA) /users/{username}
#### DELETE
Deleta a conta de um usuário junto com todas as fichas associadas.

**Parâmetros da URL**:
- **username**: O nome do usuário.

**204 No Content**: Se a deleção tiver êxito.

**403 Forbidden**: Se o usuário for admin.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```

Note que se o usuário não existir, também retorna **204 No Content**.

### (RA) /items/{type}
#### GET
Retorna uma lista json com todos os itens disponíveis para um determinado tipo. É útil para usar em requisições para adicionar ou trocar itens no inventário.

**Parâmetros da URL**:
- **type**: O tipo de item, que pode ser Ring para anéis, Bracelet para pulseiras, Collar para colares, Amulet para amuletos ou Item para itens quaisquer.

**Respostas**:

**200 OK**: Se os itens forem encontrados para o tipo especificado.
- **Headers**:  
Content-Type: application/json
- **Body**:
```json lines
[
  {
    "id": long,
    "name": "string",
    "description": "string"
  },
  ...
]
```

**400 Bad Request**: Se o parâmetro ***type*** na URL não for um dos pré-definidos.
- **Headers**:  
  Content-Type: application/json
- **Body**:
```json lines
{
  "statusCode": int,
  "message": "string"
}
```