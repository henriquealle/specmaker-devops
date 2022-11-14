package br.com.specmaker.word;

import br.com.specmaker.entity.Query;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Component
public class WordDocCreator {

    private static final String FONT_CALIBRI = "Calibri";

    public XWPFDocument gerarArquivoEspecificacao(final Query devopsQuery)
            throws IOException, URISyntaxException, InvalidFormatException {

            var documento = new XWPFDocument();
            adicionarTitulo(documento, devopsQuery.getNome());
            addCabecalhoDocumento(documento);
            //adicionarSubTitulo(documento, dados.getSubTitulo());

            adicionarParagrafo(documento, " ", false, 16);

            devopsQuery.getWorkItems().forEach(p -> {
                String index = (devopsQuery.getWorkItems().indexOf( p ) + 1) + ". ";
                String titulo = index.concat( p.getTitulo() );
                adicionarParagrafo(documento, titulo, true, 14);
                adicionarParagrafo(documento, tratarDetalhesWorkItem( p.getDetalhes() ), false, 12);
            });


            /*try (var out = new FileOutputStream( devopsQuery.getNome().concat(".docx") ) ) {
                documento.write(out);
            }*/

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

    private void adicionarSubTitulo(final XWPFDocument documento, final String subtitulo) {
        XWPFParagraph paragrafoSubTitulo = documento.createParagraph();
        paragrafoSubTitulo.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subtituloRun = paragrafoSubTitulo.createRun();
        subtituloRun.setText(subtitulo);
        subtituloRun.setColor("00CC44"); // Verde
        subtituloRun.setFontFamily(FONT_CALIBRI);
        subtituloRun.setFontSize(16);
        subtituloRun.setTextPosition(20);
        subtituloRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
    }

    private void adicionarImagem(final XWPFDocument documento, final String imagem) throws IOException, URISyntaxException, InvalidFormatException {
        XWPFParagraph paragrafoComImagem = documento.createParagraph();
        paragrafoComImagem.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun imagemRun = paragrafoComImagem.createRun();
        imagemRun.setTextPosition(20);

        var imagemPath = Paths.get(ClassLoader.getSystemResource(imagem).toURI());

        imagemRun.addPicture(Files.newInputStream(imagemPath), Document.PICTURE_TYPE_PNG,
                imagemPath.getFileName().toString(), Units.toEMU(50), Units.toEMU(50));

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
        linha.addNewTableCell().setText("Nome do Projeto");
        linha.addNewTableCell().setText("Analista responsável");
        linha.addNewTableCell().setText("Nome e e-mail Analista responsável");

        linha = tabela.createRow();
        linha.getCell(0).setText("Data da solicitação");
        linha.getCell(1).setText(
                LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd/MM/uuuu") ) );
        linha.getCell(2).setText("Pedido (uso do Sesc)");
        linha.getCell(3).setText("");

        linha = tabela.createRow();
        linha.getCell(0).setText("Gerência / Unidade Solicitante");
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
