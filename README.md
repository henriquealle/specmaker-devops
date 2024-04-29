# Introdução ao Projeto

Bem-vindo ao Projeto Specmaker!

Esse projeto tem como objetivo a geração de arquivos ".docx" concentrando documentações de projetos cadastradas em issues, cards e workitems na plataforma Azure Devops.




## Repositório Docker Hub

`docker pull henriquealle/specmaker:{tag}`

## Diagramas

[diagrama de sequência fluxo de cadastro de projeto e geração de arquivo](https://drive.google.com/file/d/1shpBywoFNjILWZyreorw9lIspLK9flC5/view?usp=sharing)

[diagrama de solução AWS](https://drive.google.com/file/d/1suLsfd0dejxweVO0nlnCjDst0ZbZBHJZ/view?usp=sharing)



# Variáveis de Ambiente

Este projeto utiliza variáveis de ambiente para configurar diferentes aspectos do sistema. As variáveis de ambiente são utilizadas para tornar a configuração mais flexível e segura, permitindo a configuração de valores sensíveis fora do código-fonte.

## Azure

- **api.token**: Token de autenticação para acessar a API do Azure DevOps.
- **api.repositorio**: Nome do projeto no Azure DevOps.
- **api.baseUrl**: URL base da API do Azure DevOps.
- **api.apiVersion**: Versão da API do Azure DevOps.

## Spring

- **spring.datasource.url**: URL do banco de dados utilizado pelo Spring.
- **spring.datasource.username**: Nome de usuário do banco de dados.
- **spring.datasource.password**: Senha do banco de dados.
- **spring.jpa.hibernate.ddl-auto**: Configuração do Hibernate para atualização automática do esquema do banco de dados.

## Logging

- **logging.level.root**: Nível de log raiz.
- **logging.pattern.console**: Padrão de formatação do console de log.

## AWS

- **aws.s3.accessKeyId**: ID da chave de acesso do AWS S3.
- **aws.s3.secretKey**: Chave secreta do AWS S3.
- **aws.s3.bucketName**: Nome do bucket do AWS S3.
- **aws.region**: Região da AWS.

## Auditoria

- **auditoria.folderName**: Caminho da pasta de auditoria para geração dos logs de alterações em banco de dados.

## Observações

- Os valores padrão fornecidos são usados se as variáveis de ambiente correspondentes não forem definidas.
- Certifique-se de fornecer valores adequados para as variáveis de ambiente, especialmente as relacionadas à segurança, como senhas e chaves de acesso.


## Contribuição

Nós adoraríamos receber contribuições para o projeto Specmaker! Se você gostaria de contribuir, por favor, siga estas diretrizes:

1. Faça um fork do repositório e clone o fork para o seu ambiente local.
2. Crie uma branch para a sua nova funcionalidade ou correção: `git checkout -b nova-funcionalidade`
3. Desenvolva sua funcionalidade ou correção e faça commits incrementais.
4. Certifique-se de adicionar testes e documentação relevante.
5. Envie um pull request para o repositório original.