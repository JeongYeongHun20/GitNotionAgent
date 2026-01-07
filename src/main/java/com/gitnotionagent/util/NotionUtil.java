package com.gitnotionagent.util;


import notion.api.v1.model.blocks.Block;
import notion.api.v1.model.blocks.HeadingTwoBlock;
import notion.api.v1.model.blocks.ParagraphBlock;
import notion.api.v1.model.common.RichTextType;
import notion.api.v1.model.databases.DatabaseProperty;
import notion.api.v1.model.pages.PageProperty;

import java.util.List;
import java.util.function.Function;

public class NotionUtil {
    private static <E, B extends Block> B createGenericBlock(
            String text,
            Function<List<PageProperty.RichText>, E> elementFactory,
            Function<E, B> blockFactory) {

        List<PageProperty.RichText> richTexts = createPageRichTextList(text);

        E element = elementFactory.apply(richTexts);
        return blockFactory.apply(element);
    }

    public static ParagraphBlock p(String text) {
        return createGenericBlock(text, ParagraphBlock.Element::new, ParagraphBlock::new);
    }

    public static HeadingTwoBlock h2(String text) {
        return createGenericBlock(text,(List<PageProperty.RichText> list) -> new HeadingTwoBlock.Element(list,null, null,null),
                HeadingTwoBlock::new);

    }
    public static List<PageProperty.RichText> createPageRichTextList(String content){
        PageProperty.RichText richText=new PageProperty.RichText(
                RichTextType.Text,
                new PageProperty.RichText.Text(content));

        return List.of(richText);
    }
    public static List<DatabaseProperty.RichText> createDBRichTextList(String content){
        DatabaseProperty.RichText richText=new DatabaseProperty.RichText(
                RichTextType.Text,
                new DatabaseProperty.RichText.Text(content));

        return List.of(richText);
    }

    public static PageProperty createTitle(String text) {
        PageProperty p = new PageProperty();
        p.setTitle(createPageRichTextList(text));
        return p;
    }

    public static PageProperty createSelect(String name) {
        PageProperty p = new PageProperty();
        p.setSelect(new DatabaseProperty.Select.Option(null, name, null,null));
        return p;
    }

    public static PageProperty createDate(String isoDate) {
        PageProperty p = new PageProperty();
        p.setDate(new PageProperty.Date(isoDate, null, null));
        return p;
    }

    public static PageProperty createUrl(String url) {
        PageProperty p = new PageProperty();
        p.setUrl(url);
        return p;
    }

    public static PageProperty createTextProperty(String text) {
        PageProperty p = new PageProperty();
        p.setRichText(createPageRichTextList(text));
        return p;
    }

}
