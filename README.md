# GerenciadorDeVendas

---

Este projeto tem como objetivo a criação de uma aplicação que permite o gerenciamento de vendas para clientes. O sistema inclui funcionalidades para cadastro de clientes, produtos e vendas, com foco na usabilidade e facilidade de atendimento ao cliente final. A interface foi projetada para ser intuitiva e simplificar o fluxo de informações.

## Funcionalidades

A aplicação inclui as seguintes funcionalidades:

### 1. **Consulta de Clientes**
Permite ao usuário realizar as seguintes ações:
- **Consultar**: Exibir a lista de clientes cadastrados.
- **Incluir**: Cadastrar novos clientes no sistema.
- **Excluir**: Remover clientes do sistema.
- **Alterar**: Editar informações de clientes já cadastrados.

### 2. **Cadastro de Cliente**
A tela de cadastro de clientes deve conter os seguintes campos:
- **Código**: Identificador único do cliente.
- **Nome**: Nome completo do cliente.
- **Limite de compra (valor)**: Limite de crédito disponível para o cliente.
- **Dia de fechamento da fatura**: Data que determina o fechamento do ciclo de compras do cliente.

## Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada para o desenvolvimento da aplicação.
- **Swing**: Biblioteca para a criação das interfaces gráficas (GUI).
- **JPA**: Para integração com o banco de dados.
- **PostgreSQL**: Banco de dados para persistência das informações.

## Como Executar o Projeto

### Pré-requisitos

- **Java 21 ou superior** instalado.
- **IDE** como IntelliJ IDEA, Eclipse ou NetBeans para execução do código.
- **Banco de dados PostgreSQL** configurado, caso seja necessário para persistência.

### Passos

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/GerenciadorDeVendas.git
   ```

2. **Configure o banco de dados**:
    - Crie o banco de dados no PostgreSQL e configure as credenciais de acesso no código (ou arquivo de configuração).

3. **Compile e execute a aplicação**:
    - Abra o projeto na sua IDE e execute a classe principal.
    - A interface será exibida, permitindo realizar o cadastro, consulta, alteração e exclusão de clientes.

## Contribuindo

Se você deseja contribuir para o projeto, siga os seguintes passos:

1. Faça um fork deste repositório.
2. Crie uma nova branch para sua feature (`git checkout -b feature/nome-da-feature`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova funcionalidade'`).
4. Envie para o repositório remoto (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais informações.