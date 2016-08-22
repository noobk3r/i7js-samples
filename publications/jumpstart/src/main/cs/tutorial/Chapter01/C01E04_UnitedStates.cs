using iText.IO.Font;
using iText.IO.Util;
using iText.Kernel.Font;
using iText.Kernel.Geom;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;
using System.IO;

namespace iText7SharpJumpstart
{
    public class C01E04_UnitedStates
    {
        public static string DATA = Directory.GetCurrentDirectory() + "\\resources\\data\\united_states.csv";
        public static void Main(string[] args)
        {
            //Define input & output paths
            string DESTDIR = "results/chapter01/";
            //Ensure the output directories exist
            Directory.CreateDirectory(DESTDIR);
            //Create the output path
            string DEST = DESTDIR + "United_States.pdf";
            //Execute pdf creation
            new C01E04_UnitedStates().createPDF(DEST);
        }

        public void createPDF(string dest)
        {
            var writer = new PdfWriter(dest);
            var pdf = new PdfDocument(writer);
            var document = new Document(pdf, PageSize.A4.Rotate());
            document.SetMargins(20, 20, 20, 20);
            var font = PdfFontFactory.CreateFont(FontConstants.HELVETICA);
            var bold = PdfFontFactory.CreateFont(FontConstants.HELVETICA_BOLD);
            var table = new Table(new float[] { 4, 1, 3, 4, 3, 3, 3, 3, 1 });
            table.SetWidthPercent(100);
            StreamReader sr = File.OpenText(DATA);
            var line = sr.ReadLine();
            process(table, line, bold, true);
            while ((line = sr.ReadLine()) != null)
            {
                process(table, line, font, false);
            }
            sr.Close();
            document.Add(table);
            document.Close();

        }

        public void process(Table table, string line, PdfFont font, bool isHeader)
        {
            var tokenizer = new StringTokenizer(line, ";");
            while (tokenizer.HasMoreTokens())
            {
                if (isHeader)
                {
                    table.AddHeaderCell(
                        new Cell().Add(
                            new Paragraph(tokenizer.NextToken()).SetFont(font)));
                }
                else
                {
                    table.AddCell(
                        new Cell().Add(
                            new Paragraph(tokenizer.NextToken()).SetFont(font)));
                }
            }
        }
    }
}
