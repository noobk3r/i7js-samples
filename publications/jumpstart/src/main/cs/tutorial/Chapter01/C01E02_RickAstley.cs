using iText.IO.Font;
using iText.Kernel.Font;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;
using System.IO;

namespace iText7SharpJumpstart
{
    public class C01E02_RickAstley
    {

        public static void Main(string[] args)
        {
            //Define input & output paths
            string DESTDIR = "results/chapter01/";
            //Ensure the output directories exist
            Directory.CreateDirectory(DESTDIR);
            //Create the output path
            string DEST = DESTDIR + "RickAstley.pdf";
            //Execute pdf creation
            new C01E02_RickAstley().createPDF(DEST);
        }

        public void createPDF(string dest)
        {
            var writer = new PdfWriter(dest);
            var pdf = new PdfDocument(writer);
            var document = new Document(pdf);
            // Create a PdfFont
            var font = PdfFontFactory.CreateFont(FontConstants.TIMES_ROMAN);
            // Add a Paragraph
            document.Add(new Paragraph("iText is:").SetFont(font));
            // Create a List
            List list = new List()
                .SetSymbolIndent(12)
                .SetListSymbol("\u2022")
                .SetFont(font);
            // Add ListItem objects
            list.Add(new ListItem("Never gonna give you up"))
                .Add(new ListItem("Never gonna let you down"))
                .Add(new ListItem("Never gonna run around and desert you"))
                .Add(new ListItem("Never gonna make you cry"))
                .Add(new ListItem("Never gonna say goodbye"))
                .Add(new ListItem("Never gonna tell a lie and hurt you"));
            // Add the list
            document.Add(list);
            document.Close();
        }
    }
}

