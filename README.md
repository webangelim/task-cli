# Task Tracker CLI

Aplicacao de linha de comando para gerenciamento de tarefas (Task Tracker) desenvolvida em Java nativo, sem o uso de bibliotecas externas para manipulacao ou persistencia de JSON.

O projeto armazena todas as tarefas em um arquivo local chamado `tasks.json` no mesmo diretorio de execucao.

---

## Requisitos

- Java Development Kit (JDK) 21 ou superior (configurado para Java 25 no `pom.xml`)
- Apache Maven 3.8+

---

## Compilacao

Para compilar o projeto com o Maven, execute o comando abaixo na raiz do repositorio:

```bash
mvn clean compile
```

Caso queira gerar o pacote `.jar`:

```bash
mvn clean package
```

---

## Como Executar

### Execucao direta com a JVM

Apos compilar com `mvn clean compile`, voce pode executar a classe `Main` passando os argumentos desejados:

```bash
java -cp target/classes Main <comando> [argumentos]
```

### Execucao via Maven Exec Plugin (opcional)

```bash
mvn compile exec:java -Dexec.mainClass="Main" -Dexec.args="<comando> [argumentos]"
```

---

## Comandos Disponiveis

### 1. Adicionar uma nova tarefa (`add`)
Cria uma nova tarefa com o status inicial `TODO`.

```bash
java -cp target/classes Main add "Descricao da tarefa"
```

Exemplo:
```bash
java -cp target/classes Main add "Comprar cafe"
# Saida: Task added successfully! (ID: 1)
```

---

### 2. Atualizar a descricao de uma tarefa (`update`)
Atualiza o texto da tarefa informada pelo ID e altera a data de atualizacao (`updatedAt`).

```bash
java -cp target/classes Main update <id> "<nova descricao>"
```

Exemplo:
```bash
java -cp target/classes Main update 1 "Comprar cafe e acucar"
# Saida: Task updated successfully! (ID: 1)
```

---

### 3. Excluir uma tarefa (`delete`)
Remove a tarefa correspondente ao ID informado do arquivo `tasks.json`.

```bash
java -cp target/classes Main delete <id>
```

Exemplo:
```bash
java -cp target/classes Main delete 1
# Saida: Task deleted successfully! (ID: 1)
```

---

### 4. Marcar tarefa em andamento (`mark-in-progress`)
Muda o status da tarefa para `IN_PROGRESS`.

```bash
java -cp target/classes Main mark-in-progress <id>
```

Exemplo:
```bash
java -cp target/classes Main mark-in-progress 1
# Saida: Task marked as in progress! (ID: 1)
```

---

### 5. Marcar tarefa como concluida (`mark-done`)
Muda o status da tarefa para `DONE`.

```bash
java -cp target/classes Main mark-done <id>
```

Exemplo:
```bash
java -cp target/classes Main mark-done 1
# Saida: Task marked as done! (ID: 1)
```

---

### 6. Listar tarefas (`list`)

- **Listar todas as tarefas:**
  ```bash
  java -cp target/classes Main list
  ```

- **Listar apenas tarefas concluidas:**
  ```bash
  java -cp target/classes Main list done
  ```

- **Listar apenas tarefas pendentes:**
  ```bash
  java -cp target/classes Main list todo
  ```

- **Listar apenas tarefas em andamento:**
  ```bash
  java -cp target/classes Main list in-progress
  ```

---

## Estrutura do Arquivo `tasks.json`

As tarefas sao salvas em um array JSON no seguinte formato:

```json
[
  {
    "id": 1,
    "description": "Comprar cafe",
    "status": "TODO",
    "createdAt": "2026-09-03T19:25:05.126346",
    "updatedAt": "2026-09-03T19:25:05.126364"
  }
]
```
