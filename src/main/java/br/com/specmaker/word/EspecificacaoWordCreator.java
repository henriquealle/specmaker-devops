package br.com.specmaker.word;

import br.com.specmaker.apiclient.azuredevops.AzureDevopsRestRestWorkItemClient;
import br.com.specmaker.entity.Query;
import br.com.specmaker.entity.WorkItemImage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Component
public class EspecificacaoWordCreator {

    private static final String FONT_CALIBRI = "Calibri";

    @Autowired
    private AzureDevopsRestRestWorkItemClient witRestClient;

    public XWPFDocument gerarArquivoEspecificacao(final Query devopsQuery)
            throws IOException, URISyntaxException, InvalidFormatException {

            var documento = new XWPFDocument();
            adicionarTitulo(documento, devopsQuery.getNome());
            addCabecalhoDocumento(documento);
            adicionarParagrafo(documento, " ", false, 16);
            adicionarTopicosIniciais(documento);

            /* adicionando os workitens e seu detalhamento no documento*/
            devopsQuery.getWorkItems().forEach(workItem -> {
                String index = "4.".concat( String.valueOf( (devopsQuery.getWorkItems().indexOf( workItem ) + 1) ) ) + ". ";
                String titulo = index.concat( workItem.getTitulo() );
                adicionarParagrafo(documento, titulo, true, 14);
                adicionarParagrafo(documento, tratarDetalhesWorkItem( workItem.getDetalhes() ), false, 12);

                workItem.getDescriptionImagens().forEach(image -> {
                    adicionarImagem(documento, image );
                });

            });

            return documento;
    }

    private void adicionarTitulo(final XWPFDocument documento, final String titulo) {
        XWPFParagraph paragrafoTitulo = documento.createParagraph();
        paragrafoTitulo.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tituloRun = paragrafoTitulo.createRun();
        tituloRun.setText(titulo);
        tituloRun.setBold(true);
        tituloRun.setFontFamily(FONT_CALIBRI);
        tituloRun.setFontSize(20);
    }

    private void adicionarTopicosIniciais(final XWPFDocument documento) {
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.TOPICO_OBJETIVO, true, 16);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.CONTEUDO_OBJETIVO, false, 12);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.TOPICO_JUSTIFICATIVA, true, 16);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.CONTEUDO_JUSTIFICATIVA, false, 12);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.TOPICO_ESCOPO, true, 16);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.CONTEUDO_ESCOPO, false, 12);
        adicionarParagrafo(documento, EspecificacaoTextosFixosHelper.TOPICO_DETALHAMENTO, true, 16);
    }

    private void adicionarImagem(final XWPFDocument documento, final WorkItemImage imagem ){

        final ByteArrayInputStream imageStream = new ByteArrayInputStream( imagem.getImgFile() );

        XWPFParagraph paragrafoComImagem = documento.createParagraph();
        paragrafoComImagem.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun imagemRun = paragrafoComImagem.createRun();
        imagemRun.setTextPosition(20);

        try {
            imagemRun.addPicture(imageStream, Document.PICTURE_TYPE_PNG,
                    "", Units.toEMU(imagem.getWidth()), Units.toEMU(imagem.getHeight()));
            imageStream.close();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void adicionarParagrafo(final XWPFDocument documento, String conteudo, boolean bold, final int fontSize) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun secaoRun = paragrafo.createRun();
        secaoRun.setFontFamily(FONT_CALIBRI);
        secaoRun.setBold(bold);
        secaoRun.setFontSize(fontSize);
        secaoRun.setText(conteudo);
    }

    private void addCabecalhoDocumento(final XWPFDocument documento) {

        var tabela = documento.createTable();
        XWPFTableRow linha = tabela.getRow(0);
        linha.getCell(0).setText("Projeto");
        linha.addNewTableCell().setText("[Informar o Nome do Projeto]");
        linha.addNewTableCell().setText("Analista responsável");
        linha.addNewTableCell().setText("[Informar o nome e e-mail do responsável]");

        linha = tabela.createRow();
        linha.getCell(0).setText("Data da solicitação");
        linha.getCell(1).setText(
                LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd/MM/uuuu") ) );
        linha.getCell(2).setText("Pedido");
        linha.getCell(3).setText("");

        linha = tabela.createRow();
        linha.getCell(0).setText("Área Solicitante");
        linha.getCell(1).setText( "" );
        linha.getCell(2).setText("Contato");
        linha.getCell(3).setText("");

    }

    private String tratarDetalhesWorkItem(String descricao){
        String str = !Objects.isNull(descricao) ? descricao.replaceAll("<.*?>", "") : "";
        str = str.replaceAll("&"+"nbsp;", " ");
        str = str.replaceAll(String.valueOf((char) 160), " ");
        str = str.replace("\u00a0","");

        return str;
    }

}
