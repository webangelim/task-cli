# Task Tracker CLI

Aplicacao de linha de comando para gerenciamento de tarefas (Task Tracker) desenvolvida em Java nativo, sem o uso de bibliotecas externas para manipulacao ou persistencia de JSON.

- Repositorio: https://github.com/webangelim/task-cli
- Proposta do Projeto: https://roadmap.sh/projects/task-tracker

O projeto armazena todas as tarefas em um arquivo local chamado `tasks.json` no mesmo diretorio de execucao.

---

## Requisitos

- Java Development Kit (JDK) 21 ou superior (configurado para Java 25 no `pom.xml`)
- Apache Maven 3.8+

---

## Compilacao e Execucao

Voce pode compilar e executar o projeto de duas maneiras:

### Opcao 1: Usando javac diretamente (Mais simples)

1. Navegue ate a pasta onde estao os arquivos `.java`:
   ```bash
   cd src/main/java
   ```

2. Compile os arquivos:
   ```bash
   javac *.java
   ```

3. Execute diretamente com `java Main`:
   ```bash
   java Main add "Comprar cafe"
   ```

---

### Opcao 2: Usando o Maven

1. Na raiz do projeto, compile:
   ```bash
   mvn clean compile
   ```

2. Execute o programa:
   - A partir da raiz do projeto:
     ```bash
     java -cp target/classes Main add "Comprar cafe"
     ```
   - Ou entrando no diretorio das classes compiladas:
     ```bash
     cd target/classes
     java Main add "Comprar cafe"
     ```

---

## Comandos Disponiveis

Nos exemplos abaixo, considere a execucao com `java Main` (dentro de `src/main/java` ou `target/classes`) ou `java -cp target/classes Main` (a partir da raiz):

### 1. Adicionar uma nova tarefa (`add`)
Cria uma nova tarefa com o status inicial `TODO`.

```bash
java Main add "Descricao da tarefa"
```

Exemplo:
```bash
java Main add "Comprar cafe"
# Saida: Task added successfully! (ID: 1)
```

---

### 2. Atualizar a descricao de uma tarefa (`update`)
Atualiza o texto da tarefa informada pelo ID e altera a data de atualizacao (`updatedAt`).

```bash
java Main update <id> "<nova descricao>"
```

Exemplo:
```bash
java Main update 1 "Comprar cafe e acucar"
# Saida: Task updated successfully! (ID: 1)
```

---

### 3. Excluir uma tarefa (`delete`)
Remove a tarefa correspondente ao ID informado do arquivo `tasks.json`.

```bash
java Main delete <id>
```

Exemplo:
```bash
java Main delete 1
# Saida: Task deleted successfully! (ID: 1)
```

---

### 4. Marcar tarefa em andamento (`mark-in-progress`)
Muda o status da tarefa para `IN_PROGRESS`.

```bash
java Main mark-in-progress <id>
```

Exemplo:
```bash
java Main mark-in-progress 1
# Saida: Task marked as in progress! (ID: 1)
```

---

### 5. Marcar tarefa como concluida (`mark-done`)
Muda o status da tarefa para `DONE`.

```bash
java Main mark-done <id>
```

Exemplo:
```bash
java Main mark-done 1
# Saida: Task marked as done! (ID: 1)
```

---

### 6. Listar tarefas (`list`)

- **Listar todas as tarefas:**
  ```bash
  java Main list
  ```

- **Listar apenas tarefas concluidas:**
  ```bash
  java Main list done
  ```

- **Listar apenas tarefas pendentes:**
  ```bash
  java Main list todo
  ```

- **Listar apenas tarefas em andamento:**
  ```bash
  java Main list in-progress
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
