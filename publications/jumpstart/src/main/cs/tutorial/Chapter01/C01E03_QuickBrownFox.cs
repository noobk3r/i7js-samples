using iText.IO.Image;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;
using System.IO;

namespace iText7SharpJumpstart
{
    public class C01E03_QuickBrownFox
    {
        public static string DOG = Directory.GetCurrentDirectory() + "\\resources\\img\\dog.bmp";
        public static string FOX = Directory.GetCurrentDirectory() + "\\resources\\img\\fox.bmp";
        public static void Main(string[] args)
        {
            //Define input & output paths
            string DESTDIR = "results/chapter01/";
            //Ensure the output directories exist
            Directory.CreateDirectory(DESTDIR);
            //Create the output path
            string DEST = DESTDIR + "QuickBrownFox.pdf";
            //Execute pdf creation
            new C01E03_QuickBrownFox().createPDF(DEST);
        }

        public void createPDF(string dest)
        {
            var writer = new PdfWriter(dest);
            var pdf = new PdfDocument(writer);
            var document = new Document(pdf);
            var fox = new Image(ImageDataFactory.Create(FOX));
            var dog = new Image(ImageDataFactory.Create(DOG));
            var p = new Paragraph("The quick brown ")
                        .Add(fox)
                        .Add(" jumps over the lazy ")
                        .Add(dog);
            document.Add(p);
            document.Close();
        }
    }
}
