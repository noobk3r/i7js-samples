Tue Feb 16 09:53:52 2016

    Review sandbox.objects samples
    
    DEVSIX-423


Tue Feb 16 09:48:27 2016

    Final preparation of samples before splitting off
    
    

Mon Feb 15 17:36:40 2016

    Prepare samples for splitting off to separate repository
    
    

Mon Feb 15 12:56:42 2016

    Introduce LineSeparator element. Fix samples, add links to Jira issues
    
    DEVSIX-423


Mon Feb 15 11:10:09 2016

    Content wrapping on stamping mode: page rotation issue
    
    (DEVSIX-431)


Sun Feb 14 14:26:28 2016

    Port partially chapter 12 of the book. Minor additions
    
    

Sat Feb 13 14:43:44 2016

    Review PdfObjectWrappers indirect status; remove PdfDocument from several wrappers; refactor getDocument() methods.
    
    Introduced new property of the PdfObjectWrappers which defines if wrapped object must be indirect in the resultant document (isWrappedObjectMustBeIndirect method).
Removed PdfDocument from colorspaces, some annotations, name tree and nodes, shadings, functions and some other wrappers as well.
Removed PdfObject#getDocument method: this method would only work, if object was indirect. So to emphasize the need of indirect status, you will now have to call pdfObject.getIndirectReference().getDocument() explicitly. Also the getReader() and getWriter() methods are now moved to the PdfIndirectReference, because they fully depend on getDocument() method and have the same disadvantages.
Removed PdfObjectWrapper#getDocument() method: this method only works for wrappers that have indirect objects behind them, which is not very obvious. From now this method is moved to the wrappers that really need the PdfDocument instance for their work. These wrappers can work only with the indirect objects, so now they throw an exception if someone would try to wrap a direct object.
Improved CompareTool: it now compares objects indirect status.

DEVSIX-222


Sat Feb 13 12:03:42 2016

    Fix minor bug in PdfNode. Revise some samples
    
    

Sat Feb 13 11:32:49 2016

    Add copyright headers to samples
    
    

Sat Feb 13 11:32:41 2016

    Port missing samples. Revise some samples
    
    

Fri Feb 12 10:04:16 2016

    Content wrapping on stamping mode: page rotation issue
    
    (DEVSIX-431)


Fri Feb 12 09:35:57 2016

    Fix bug in PdfCanvas
    
    

Thu Feb 11 15:00:28 2016

    Fix issue with a0 character in toUnicode CMap
    
    

Thu Feb 11 11:55:29 2016

    Add ColumnDocumentRenderer convenient class for multi-column Document Rendering
    
    

Wed Feb 10 11:13:53 2016

    Samples: missing features from sandbox.stamper
    
    (DEVSIX-402)


Wed Feb 10 10:48:58 2016

    Forms: missing features from itext5
    
    DEVSIX-455


Wed Feb 10 10:12:44 2016

    Fix issue with serializing pages in smartmode.
    
    

Tue Feb 9 17:07:40 2016

    Fix case sensitive path variable
    
    

Tue Feb 9 12:13:37 2016

    Rename SmartCopyMode to SmartMode
    
    

Tue Feb 9 10:21:31 2016

    Unification of copyTo methods.
    
    

Tue Feb 9 08:07:19 2016

    Port new samples
    
    

Mon Feb 8 22:11:07 2016

    Fix table layout area calculation in case of cell Nothing layout status. Footers are now drawn correctly in that case. Unignore tests
    
    DEVSIX-439


Mon Feb 8 15:34:05 2016

    Update Logo sample
    
    

Mon Feb 8 10:11:22 2016

    Fix copying of tagged link annotations, fix cmp files
    
    

Mon Feb 8 11:36:17 2016

    Fix Paragraph bottom area calculation. Fix samples
    
    

Sun Feb 7 13:55:39 2016

    Add copyright headers to samples
    
    

Sun Feb 7 13:55:17 2016

    Port new samples
    
    

Thu Feb 4 12:48:17 2016

    missing features from book part2
    
    DEVSIX-398


Wed Feb 3 13:29:21 2016

    increase test time out
    
    

Wed Feb 3 12:58:45 2016

    Samples: missing features from book part1
    
    (DEVSIX-397)


Tue Feb 2 17:24:57 2016

    Fix more cmp files
    
    Same problem as in [f69a1404a5].


Tue Feb 2 12:40:42 2016

    Remove parser.GraphicState class, CanvasGraphicState class and parser package refactoring
    
    Moved Path and connected to it classes from parser package to geom package.
CanvasGraphicState fields now have default values according to the spec. Also CanvasGraphicState now tracks the ctm.
Instead of the parser.GraphicState there is a new internal class ParserGraphicState which extedns the CanvasGraphicState and encapsulates the logic of clipping path tracking.


Tue Feb 2 13:14:52 2016

    Port some new samples. Minor changes
    
    

Tue Feb 2 13:09:31 2016

    Fix minor bug in LinkRenderer
    
    

Mon Feb 1 17:34:27 2016

    Updating develop poms back to pre merge state
    
    

Mon Feb 1 17:34:26 2016

    Merge branch 'master' into develop
    
    

Mon Feb 1 17:34:25 2016

    updating develop poms to master versions to avoid merge conflicts
    
    

Mon Feb 1 17:28:09 2016

    updating poms for branch'release/7.0.0-RC.20160201' with non-snapshot versions
    
    

Mon Feb 1 17:28:06 2016

    updating poms for 7.0.0-RC.20160201 branch with snapshot versions
    
    

Mon Feb 1 16:26:10 2016

    Add jgit-flow plugin
    
    

Mon Feb 1 11:50:17 2016

    Fix issue with FontFile3 font stream subtype.
    
    

Sun Jan 31 14:43:05 2016

    Remove PdfFontFactory#createStandardFont() and #createType1Font()
    
    Remove #createStandardFont() in favour to #createFont().
If user wants to create specific PdfType1Font, he should firstly create FontProgram with FontFactory.createType1Font()


Fri Jan 29 17:15:09 2016

    Update version and name.
    
    

Fri Jan 29 16:57:26 2016

    Rename model -> layout.
    
    

Fri Jan 29 16:46:30 2016

    Rename core -> kernel.
    
    

Fri Jan 29 16:26:36 2016

    Rename basics -> io.
    
    

Fri Jan 29 16:07:33 2016

    Port new acroforms samples. Minor additions
    
    

Fri Jan 29 15:45:01 2016

    Samples: missing features from book part1
    
    DEVSIX-397


Fri Jan 29 15:30:31 2016

    Move PdfException to core. Create basics.IOException.
    
    

Fri Jan 29 14:35:35 2016

    Fix PdfAcroform flattening bug with circular references when xObject's resources and page resources are a same object and we later want to add xObject to the page resources
    
    

Fri Jan 29 14:05:53 2016

    Fix a minor bug in RootElement
    
    

Fri Jan 29 12:47:30 2016

    Rename PdfAcroForm#flatFields to PdfAcroForm#flattenFields
    
    

Fri Jan 29 12:09:37 2016

    Move basics.geom to core.geom. Create basics.util package.
    
    

Fri Jan 29 10:57:48 2016

    Add copyright headers to samples
    
    

Thu Jan 28 22:04:39 2016

    Port some missing samples. Add a method to compute user password
    
    

Thu Jan 28 17:08:20 2016

    Add readme
    
    

Thu Jan 28 14:57:41 2016

    Update cmp file
    
    

Thu Jan 28 14:26:41 2016

    Update document font related samples
    
    DEVSIX-399


Thu Jan 28 14:09:23 2016

    Add .gitattributes and .gitignore to samples
    
    They are a copy of the files in the root of the project.


Thu Jan 28 12:23:06 2016

    Add copyright headers to samples
    
    

Thu Jan 28 13:37:55 2016

    Port sandbox.security. Fix a sample cmp file
    
    

Thu Jan 28 13:17:50 2016

    Fix a number of sandbox samples
    
    

Thu Jan 28 12:16:49 2016

    Fix PdfFormField appearance generation in corner cases and fix a sample
    
    

Thu Jan 28 11:44:31 2016

    Form fields: fix bug with storing font and text (PushButton case). Using model for creating field appearances. Support complex scripts (Arabic/Indic) for field appearances. Minor bug fixes for model
    
    DEVSIX-418


Thu Jan 28 08:30:13 2016

    Replace itext6 with itext7 in comments, local variable names, etc.
    
    

Wed Jan 27 17:48:57 2016

    Revise, refactor and correct sandbox samples
    
    

Wed Jan 27 15:28:39 2016

    Add handy method to set page rotation. Correct some samples
    
    

Wed Jan 27 15:04:24 2016

    Revise and correct some sandbox.stamper samples.
    
    

Wed Jan 27 14:13:40 2016

    Layout: stop adding content if currentArea becomes null
    
    

Wed Jan 27 13:15:20 2016

    Revise and correct some sandbox.tables samples . Port some missing samples
    
    

Wed Jan 27 12:22:49 2016

    Add copyright headers to samples
    
    Closes DEVSIX-426


Wed Jan 27 11:59:23 2016

    PdfSpotColor implementation in samples
    
    DEVSIX-412


Wed Jan 27 11:15:43 2016

    Fix field appearance generation in case of custom font provided. Fix field value creation (non-latin Unicode chars did not work at all). Reorder static method arguments to make them more consistent. Make appearance generation methods protected
    
    DEVSIX-418


Wed Jan 27 10:53:08 2016

    Refactor CompareTool to provide encrypted documents comparison. Add a sample
    
    

Wed Jan 27 08:54:18 2016

    Rename getNextArea to UpdateCurrentArea to avoid misunderstandings
    
    

Tue Jan 26 21:25:47 2016

    Port some missing samples. Refactor some samples
    
    

Tue Jan 26 15:31:41 2016

    Create PdfFontFactory and move all create methods from PdfFont
    
    DEVSIX-421


Tue Jan 26 15:21:42 2016

    PdfFont refactoring.
    
    Made PdfFont abstract class. Now any PdfFont could be created only by PdfFont.create method.

DEVSIX-421


Tue Jan 26 11:42:46 2016

    Type3 fonts refactoring
    
    DEVSIX-232


Tue Jan 26 10:43:52 2016

    Failed test createEmptyDocumentWithXmp
    
    (DEVSIX-407)


Tue Jan 26 09:55:15 2016

    Unignore a test
    
    

Tue Jan 26 09:44:55 2016

    Fix and unignore a sample
    
    

Tue Jan 26 09:24:57 2016

    Wrap old page content in stamping mode on PdfCanvas level. Avoid unnecessary wrapping on model level in case it is a stamper, but a new page
    
    

Tue Jan 26 06:51:55 2016

    Refactor some samples. Minor corrections
    
    

Tue Jan 26 06:37:07 2016

    Port some missing samples. Revise chapter 6. Minor additions
    
    

Mon Jan 25 13:32:34 2016

    Merge utils with core, remove xmlsign
    
    (DEVSIX-410)


Mon Jan 25 12:34:33 2016

    Make sure the target folder exists
    
    

Mon Jan 25 11:44:45 2016

    Improve TextRenderer performance. Fix a sample
    
    

Mon Jan 25 11:39:38 2016

    Remove PdfDocument from PdfAnnotations
    
    

Mon Jan 25 10:48:35 2016

    Port missing examples from chapters 11, 13, 14, 15, 16
    
    

Mon Jan 25 10:46:53 2016

    Add internal tests module to the parent pom file. Remove PdfDocument from PdfAction
    
    DEVSIX-380


Mon Jan 25 10:29:08 2016

    make Point inner class, rename canvas image package to wmf
    
    (DEVSIX-417)


Fri Jan 22 15:05:05 2016

    Rename core.testutils to core.utils
    
    

Fri Jan 22 14:52:46 2016

    move canvas to core
    
    (DEVSIX-417)


Fri Jan 22 11:46:54 2016

    Merge branch 'feature/typography' into develop
    
    Conflicts:
	canvas/src/test/java/com/itextpdf/canvas/PdfFontTest.java
	core/src/main/java/com/itextpdf/core/font/PdfType0Font.java
	typography/src/test/java/com/itextpdf/typography/BidiTest.java
	typography/src/test/java/com/itextpdf/typography/DevanagariTest.java
	typography/src/test/java/com/itextpdf/typography/KerningTest.java


Fri Jan 22 11:28:05 2016

    Add dependency for samples on typography
    
    

Fri Jan 22 11:22:45 2016

    Introduce DrawContext in layout module
    
    

Fri Jan 22 10:45:15 2016

    Move CompareTool to pdftest module
    
    DEVSIX-416


Fri Jan 22 09:21:20 2016

    Type0Document font. Add decode methods to PdfFont.
    
    Currently we support only Identity-H CMap.

DEVSIX-339


Thu Jan 21 11:33:08 2016

    Dependencies on iText 5
    
    (DEVSIX-406)


Wed Jan 20 13:03:13 2016

    Rename getNumOf -> getNumberOf (pages, components, etc)
    
    

Wed Jan 20 10:30:14 2016

    Program to interfaces: samples module
    
    DEVSIX-424


Mon Dec 28 12:26:53 2015

    Port partially chapter15, chapter12, some other samples. Minor fixes
    
    

Tue Jan 19 13:21:51 2016

    Add partial flushing support of tag structure
    
    DEVSIX-388


Mon Jan 18 20:19:06 2016

    Remove PdfDocument from PdfFont constructor.
    
    PdfFont become indirect after adding to PdfCanvas (PdfCanvas#setFontAndSize()).
PdfType3Font still need PdfDocument.

DEVSIX-329


Mon Jan 18 13:12:00 2016

    Change API for PdfFormField#createRadioGroup
    
    Switch places of value and name parameters, to comply with all other
static creator methods


Sat Jan 16 12:57:09 2016

    Update PdfTrueTypeFont writing
    
    DEVSIX-383


Sat Jan 16 11:05:52 2016

    Improve font encodings. Add more PdfFont properties
    
    DEVSIX-383


Fri Jan 15 11:48:32 2016

    added missing features from book part3 chapter10
    
    DEVSIX-399


Thu Jan 14 12:17:01 2016

    Get rid of kerning in TextRenderer. Move to applying kerning to a GlyphLine beforehand and then deal with any character advance
    
    

Thu Jan 14 14:58:04 2016

    Document Type 1 font with simple encoding and toUnicode map.
    
    DEVSIX-383


Thu Jan 14 09:11:57 2016

    GifImageHelper improvements and refactoring
    
    DEVSIX-361


Wed Jan 13 15:48:09 2016

    Indic text correct copy-pasting investigation and prototyping
    
    DEVSIX-404


Wed Jan 13 16:06:43 2016

    Fix compilation fail (local fields can be `Map`s)
    
    

Wed Jan 13 15:21:25 2016

    Move hard coded dependency versions to variables
    
    

Sun Jan 10 19:36:38 2016

    Support glyph placement offsets
    
    DEVSIX-369


Fri Jan 8 15:59:50 2016

    Add Maven configuration for PIT mutation testing
    
    http://pitest.org/


Wed Jan 6 16:25:58 2016

    Fix maven warnings
    
    

Wed Jan 6 15:59:14 2016

    Update cursor position after glyph with offset
    
    DEVSIX-369


Wed Jan 6 13:08:09 2016

    Add new methods for add file attachments to a document.
    
    DEVSIX-395


Wed Jan 6 10:29:51 2016

    Fix showTextAligned misplacing problem caused by incorrect copy method of PageSize as Rectangle subclass
    
    DEVSIX-360


Wed Dec 30 14:11:00 2015

    Add offsets to Glyph
    
    DEVSIX-369


Tue Dec 29 10:48:02 2015

    Write text content as hexed string in case Type0Font.
    
    

Mon Dec 28 14:58:25 2015

    Fix list issue with list symbol overlapping content
    
    

Mon Dec 28 14:07:25 2015

    Fix list symbol indentation in case of second time layouting
    
    

Mon Dec 28 13:42:29 2015

    Fix setWidth(0) problem with headers and footers in tables. setWidthPercentage(100) is, however, preferred in this case. Fix samples
    
    

Mon Dec 28 09:33:21 2015

    Add a possibility to set list start value. Refactor AbstractRenderer a bit
    
    

Mon Dec 28 08:57:51 2015

    Improve ToUnicode dictionary
    
    DEVSIX-322


Sun Dec 27 16:17:00 2015

    Revise part 1. Some fixes and minor additions
    
    Revise the usage of CMYK, paragraph's leading, table positioning, etc.


Fri Dec 25 21:07:50 2015

    Port chapter 16. Minor fixes
    
    

Fri Dec 25 10:44:55 2015

    Port chapter 13 samples. Minor fixes and additions
    
    Check whether we have PdfDictionary already or only indirect reference in some PdfFormField methods.
Add handy addCell methods as in itext5


Wed Dec 23 19:22:22 2015

    Port chapter 11 samples. Minor Table additions
    
    

Wed Dec 23 11:51:49 2015

    AcroForm flattener throws NullPointer on form button value <</AS/Off>>
    
    There're also some fixes in PdfNameTree

DEVSIX-365


Tue Dec 22 12:03:12 2015

    Merge remote-tracking branch 'origin/develop' into develop
    
    

Tue Dec 22 12:02:30 2015

    NaN parameters of Cell occupiedArea
    
    DEVSIX-358


Tue Dec 22 12:02:09 2015

    Investigate different timezone problem
    
    (DEVSIX-367)


Mon Dec 21 13:11:45 2015

    Fix table fixed position issue. Fix a number of samples
    
    

Fri Dec 18 18:48:10 2015

    Fix a number of samples and a couple of minor bugs in code
    
    DEVSIX-366


Thu Dec 17 13:27:16 2015

    Introduce Style class which aggregates properties and can be applied to arbitrary number of elements (analogue of classes in html/css)
    
    DEVSIX-343


Thu Dec 17 12:37:37 2015

    Fix PdfStream#setData implementation and unignore a sample
    
    

Thu Dec 17 11:26:38 2015

    Implement ZapfDingbatsNumberList analogue (add new types to ListNumberingType enum) and rendering logic
    
    

Thu Dec 17 11:41:19 2015

    Layout: more pleasant way to define custom renderers for elements
    
    method getNextRenderer were added to IRenderer. This method returns a new Instance of IRenderer. This method should be overridden  for correct behaviour if a user wants to use custom renderer.
Also some refactoring affecting Elements and Renderers was done.

DEVSIX-344


Thu Dec 17 09:12:29 2015

    Fix TODOs in sandbox/images samples
    
    (DEVSIX-355)


Wed Dec 16 12:35:34 2015

    Auto detect symbolic fonts if user doesn't set any encoding.
    
    

Wed Dec 16 12:05:07 2015

    Improve symbolic fonts handling
    
    

Tue Dec 15 11:07:27 2015

    Update BaseFont property for CIDFontType0
    
    

Mon Dec 14 20:13:36 2015

    Add sandbox.merge samples. Revise samples a bit
    
    

Mon Dec 14 16:38:20 2015

    Move GlyphLine creation methods to PdfFont level.
    
    DEVSIX-233


Mon Dec 14 11:35:08 2015

    Fix NullPointerException in TextRenderer in case there is no unicode value in a glyph. Add new Devanagari tests
    
    

Sat Dec 12 10:02:09 2015

    Refactor some samples
    
    Use cmp-files instead of running tests again in order to prevent exceptions caused by parallel test running.
Some refactoring.


Fri Dec 11 10:34:47 2015

    Improve handling of notdef symbols
    
    

Thu Dec 10 16:04:07 2015

    A great font refactoring
    
    DEVSIX-312, DEVSIX-267, DEVSIX-346


Wed Dec 9 20:31:54 2015

    Revise some samples
    
    

Wed Dec 9 11:34:55 2015

    Anchor element alternative
    
    DESTINATION and ACTION properties were added to layout Property.
Annotation sandbox samples were revied and fixed

DEVSIX-332


Tue Dec 8 15:29:19 2015

    Revise some samples
    
    

Tue Dec 8 15:06:17 2015

    Port book: chapter6, chapter7, chapter8, chapter10, chapter14
    
    

Thu Dec 3 14:32:06 2015

    Reviewed annotation TODO's
    
     addFileAttachment method was added to PdfDocument. PdfNameTree and PdfNode were changed a little.

DEVSIX-354


Wed Dec 2 11:53:47 2015

    Impove CIDSet generation in iText6
    
    (DEVSIX-353)


Tue Dec 1 13:07:07 2015

    Vertical alignment for cells
    
    DEVSIX-345


Mon Nov 30 13:47:45 2015

    Missing methods for acroform sandbox samples were implemented
    
    DEVSIX-335


Tue Nov 24 08:49:37 2015

    Avoid NullPointerException in TrueTypeFont. Add Devanagari ignored test
    
    

Fri Nov 20 11:00:01 2015

    Layout module: table margins were implemented
    
    DEVSIX-327


Thu Nov 19 08:54:33 2015

    Remove image filter handlers that do nothing. Fix sample from sandbox
    
    

Wed Nov 18 18:06:40 2015

    Refactor some samples
    
    

Wed Nov 18 18:05:31 2015

    Port events samples
    
    

Wed Nov 18 14:25:48 2015

    Fix samples in accordance with new text alignment properties
    
    

Wed Nov 18 11:48:24 2015

    Layout: introduce text alignment inherited property which is responsible for aligning of text (former horizontal alignment)
    
    Horizontal alignment is now not inherited and is responsible for aligning an element with fixed width relative to its parent.
This allows to center an element, say, a table with 80 percent width and avoid recursive undesirable centering of inner text

DEVSIX-320


Wed Nov 18 09:39:20 2015

    Minor additions in lowagie classes
    
    

Wed Nov 18 09:28:58 2015

    Port book part1 samples
    
    

Wed Nov 18 08:58:00 2015

    Add scaling Image only by width or height
    
    

Mon Nov 16 11:20:06 2015

    Get rid of Java <= 7 vs Java 8 differences: replace HashMap/Set with LinkedHashMap/Set
    
    

Mon Nov 16 10:13:17 2015

    Merge branch 'feature/layout_otf_features_bridge' into develop
    
    Conflicts:
	core/src/main/java/com/itextpdf/core/crypto/StandardDecryption.java


Mon Nov 16 10:11:33 2015

    Minor layout fixes after lookup table type 3 was introduced
    
    DEVSIX-311


Fri Nov 13 19:31:49 2015

    Support ligatures + bidi reordering at layout level. Introduce two modes for liga feature - language specific and generic (all liga tables). Apply script specific liga feature at model level as some fonts supporting Arabic do not have rlig feature, but only have liga
    
    

Fri Nov 13 12:24:03 2015

    Implement filling and removing XFA forms
    
    (DEVSIX-324)


Thu Nov 12 15:31:01 2015

    Implement filling and removing XFA forms
    
    (DEVSIX-324)


Thu Nov 12 13:18:38 2015

    fix issue with subsetting.notdef glyph
    
    DEVSIX-311


Thu Nov 12 11:25:01 2015

    Make forms field classes constructors protected (corresponding static methods of PdfFormField should be called instead). Fix a sample
    
    

Wed Nov 11 11:39:26 2015

    Initial prototyping of bridge between layout and OTF features (support for Arabic shaping)
    
    DEVSIX-311


Wed Nov 11 11:18:35 2015

    Fields merging with duplicate names during the copy process was implemented.
    
    Also some minor fixes and improvements for fields were added.

DEVSIX-321


Wed Nov 11 09:18:50 2015

    Fix bug with getting document in LinkRenderer
    
    

Tue Nov 10 14:19:26 2015

    Incorrect rendering of field value
    
    (DEVSIX-323)


Tue Nov 10 13:41:12 2015

    Fix unicode character display when movie database file is used (book samples)
    
    

Tue Nov 10 11:41:37 2015

    Fix bug with not flushing writer properly
    
    

Mon Nov 9 11:24:46 2015

    Wrong trailer creation in stamping mode was fixed.
    
    DEVSIX-328


Mon Nov 9 11:12:03 2015

    Reorder PdfFormField#setValue parameters. Fix samples
    
    

Thu Nov 5 12:40:26 2015

    Fix Color import
    
    

Wed Nov 4 17:55:47 2015

    Merge remote-tracking branch 'origin/develop' into develop
    
    Conflicts:
	samples/src/test/java/com/itextpdf/samples/sandbox/objects/ColoredText.java


Wed Nov 4 17:54:21 2015

    Ported PdfContentStreamProcessor and related classes.
    
    Resolves: DEVSIX-318


Wed Nov 4 13:29:43 2015

    Radio group is not working correctly in multipage layout
    
    DEVSIX-326


Wed Nov 4 09:31:00 2015

    More work on samples
    
    

Tue Nov 3 13:14:36 2015

    Add a new createRegisteredFont method overload for PdfFont. Edit TODOs in a couple of samples. Fix LiberationSans font sample
    
    

Tue Nov 3 12:35:49 2015

    Update TODOs in samples
    
    

Fri Oct 30 15:31:04 2015

    Move margins from PageSize to document settings
    
    DEVSIX-309


Thu Oct 29 12:51:51 2015

    Indirect objects are not written to output in stamping mode
    
    (DEVSIX-291)


Wed Oct 28 15:05:01 2015

    Refactor split renderers creation in TextRenderer
    
    

Wed Oct 28 13:43:40 2015

    More work on custom renderer samples
    
    

Wed Oct 28 09:52:02 2015

    Initial prototyping of canvas layouting. Fix a number of samples
    
    DEVSIX-239


Tue Oct 27 16:03:49 2015

    Layout: LargeTable borders implementation and some border style fixes.
    
    DEVSIX-303


Tue Oct 27 11:23:00 2015

    Text is not visible on stamping. Wrapping graphic state
    
    (DEVSIX-281)


Tue Oct 20 10:17:50 2015

    fix cmp file for sample
    
    

Tue Oct 20 09:57:17 2015

    Jpeg compression problem
    
    (DEVSIX-287)


Fri Oct 16 07:36:47 2015

    Refactor sandbox.book
    
    

Thu Oct 15 14:41:02 2015

    rename PdfDocument#getIndirectReferences() method back to listIndirectReferences()
    
    

Thu Oct 15 14:33:08 2015

    fix failure build
    
    

Tue Oct 13 19:01:05 2015

    Refactor object samples
    
    

Tue Oct 13 18:50:50 2015

    Port interactive and parse samples. Add some DoNothingFilter's
    
    One need some DoNothingFilters for samples running.
See ExtractStreams sample


Tue Oct 13 18:15:21 2015

    Port objects samples
    
    

Tue Oct 13 17:26:54 2015

    Fix problem with ArtBox and TrimBox
    
    Previously setting the ArtBox caused Exception,
because itext created  a Trimbox by default.
See the example, ported from samples


Tue Oct 13 16:49:09 2015

    Port font samples. Add method to receive PdfObjects in PdfDocument number
    
    

Mon Oct 12 10:21:28 2015

    Add PdfStream#setData method
    
    DEVSIX-286


Sat Oct 10 12:52:40 2015

    PdfReader double reading problem
    
    (DEVSIX-283)


Fri Oct 9 16:40:51 2015

    Refactor stream writing to document; add possibility to change existing streams compression level in stamping mode
    
    DEVSIX-297


Fri Oct 9 09:46:35 2015

    Add PdfImageXObject#getImageBytes method
    
    New method to get decoded bytes of the image

DEVSIX-285


Thu Oct 8 16:28:03 2015

    Layout: table borders.
    
    DEVSIX-205


Tue Oct 6 16:28:59 2015

    Port annotations samples
    
    

Mon Oct 5 10:20:06 2015

    Add new tests for removing unused objects functionality
    
    DEVSIX-284


Fri Oct 2 10:04:10 2015

    Organize imports
    
    Import order:
1. com.itextpdf
2. com.lowagie
3. java
4. javax
5. org
6. com

No wildcard imports, see code guidelines https://conf.itextsupport.com/display/DEV/Code+guidelines


Thu Oct 1 11:47:17 2015

    Make PageSize margins order in methods and constructors more concise. Apply default margins in case of laying content out on existing pages. Fix samples
    
    DEVSIX-280


Wed Sep 30 19:01:11 2015

    Minor PdfExtGState refactoring
    
    

Wed Sep 30 23:02:53 2015

    Hyphenation: fix reading files from default hyph module location. Fix soft hyphenation. Add soft hyphenation test
    
    

Wed Sep 30 13:33:17 2015

    Hyphenation: model level. Port hyphenation samples
    
    DEVSIX-256


Sat Sep 26 10:26:17 2015

    Move ISplitCharacters to splitting package
    
    

Thu Sep 24 16:49:12 2015

    Fix cmp files
    
    

Thu Sep 24 16:32:01 2015

    Update BidiAlgorithm copyright notice. Add new samples for right-to-left text
    
    

Thu Sep 24 13:32:24 2015

    Rename Event name constants in accordance with Java code convention
    
    

Thu Sep 24 11:41:13 2015

    Update cmp files
    
    

Thu Sep 24 11:22:58 2015

    Improve lineheight calculation
    
    If we find WinAscender and WinDescender in the font and they are not equal to TypoAscender and TypoDescender correspondingly, we use their values to calculate line height. Otherwise, we use TypoAscender and TypoDescender values scaled by a factor of 1.2 (to fit diacritic sybmols in line area). Multiplied leading is calculated based on the ascender/descender values above scaled by font size and the specified multiplier. It does not therefore equal to the fontSize * leadingScaleFactor in the resultant pdf and may be more or less than that value. If the multiplied leading factor is equal to one, the lines will be placed side-by-side.

DEVSIX-264


Wed Sep 23 13:35:20 2015

    pdf/a: font check
    
    Add CIDSet key to any Type0 font based on TrueType font.
Verify embedding state of font for PDF/A.

Resolved: DEVSIX-244


Tue Sep 22 17:50:02 2015

    Layout: initial right-to-left text writing. Bidi algorithm
    
    DEVSIX-130


Tue Sep 22 14:03:38 2015

    Change some sandbox examples due to latest changes
    
    

Tue Sep 22 13:32:49 2015

    Fix CompareTool bug when comparing font subsets. Migrate several tests to compare by content
    
    DEVSIX-292


Tue Sep 22 13:30:34 2015

    Port acroforms.reporting from sandbox.samples
    
    

Tue Sep 22 13:17:38 2015

    Port images from sandbox.samples
    
    

Tue Sep 22 12:42:58 2015

    Fonts: subsetting issue in embedFont mode - huge resultant files
    
    (DEVSIX-275)


Tue Sep 22 12:03:52 2015

    Revise acroforms sandbox.samples due to latest changes
    
    

Tue Sep 22 10:42:56 2015

    A few bugs with acroforms were fixed
    
    DEVSIX-268


Tue Sep 22 10:19:56 2015

    Change cmp files in some sandbox tests
    
    

Tue Sep 22 10:11:10 2015

    Port stamper sandbox.samples
    
    

Tue Sep 22 09:31:41 2015

    Port barcode sample from sandbox
    
    

Mon Sep 21 10:58:39 2015

    Tests refactoring
    
    (DEVSIX-216)


Mon Sep 21 08:08:41 2015

    Merge remote-tracking branch 'origin/develop' into develop
    
    

Mon Sep 21 08:08:20 2015

    Refactored PdfResources
    
    Moved all the code related to generating new resource name to ResourceNumber class. Renamed the latter one to ResourceNameGenerator. Added PdfName.hashCode.


Fri Sep 18 14:19:04 2015

    Acroforms: partial form flattening
    
    DEVSIX-269


Wed Sep 16 08:35:14 2015

    Fix source name in some test
    
    

Wed Sep 16 08:05:42 2015

    Fix pageSize-getting problem
    
    See sample which has been causing Exception before the change


Tue Sep 15 16:42:00 2015

    AcroForm: allow add annotation of the field to page.
    
    DEVSIX-265


Mon Sep 14 14:46:55 2015

    More work on samples
    
    

Mon Sep 14 12:15:30 2015

    The cmp file for SimpleTable08 sample was added.
    
    

Mon Sep 14 10:24:23 2015

    Creating FormXObjects by pages
    
    DEVSIX-238


Fri Sep 11 15:10:14 2015

    convenient font creation methods
    
    DEVSIX-258


Thu Sep 10 16:07:15 2015

    Layout: TextRenderer refactoring: migrate from using String in favor of using an int[] array with unicode char codes
    
    

Thu Sep 10 11:50:30 2015

    Introduce ISplitCharacters interface. More accurate line splitting in layout
    
    

Thu Sep 10 15:45:33 2015

    add default trimbox to PdfPage, fix cmp file
    
    

Wed Sep 9 16:49:34 2015

    PDF/A: Metadata
    
    fix some bugs, some refactoring

(DEVSIX-248)


Wed Sep 9 10:25:09 2015

    Add dependency to provide samples.acroforms running
    
    

Wed Sep 9 10:21:28 2015

    Revise some sandbox examples
    
    

Wed Sep 9 10:19:03 2015

    Port itext5 sandbox.acroforms to itext6
    
    

Tue Sep 8 10:55:23 2015

    Minor codebase changes. More work on samples
    
    

Tue Sep 8 09:53:13 2015

    Layout: add possibility to specify element width in percents. Fix a number of samples
    
    

Mon Sep 7 09:26:17 2015

    Barcodes minor refactoring. New convenience methods. Barcode1d#fitWidth(float) method implementation. Generalize barcode classes usage - setCode() common method. Fix auto scaling of images. New example of convenient barcode fitting into layout
    
    DEVSIX-177


Fri Sep 4 08:29:29 2015

    Code clean up
    
    

Fri Sep 4 14:55:58 2015

    Use Surefire for Unit Tests and Failsafe for Integration Tests
    
    

Fri Sep 4 14:08:27 2015

    Decorate test classes with test category annotations
    
    

Thu Aug 27 12:43:57 2015

    Fix margins problem in some sandbox.tables tests
    
    

Wed Aug 26 18:36:46 2015

    Add cmp file to ImageNextToText test
    
    

Wed Aug 26 18:32:52 2015

    Fix rotated image scaling
    
    Add ported itext5 sandbox.tables's test as addition


Wed Aug 26 18:26:18 2015

    Port itext5 sandbox.tables to itext6
    
    

Wed Aug 26 15:18:30 2015

    Add PdfDocument to the PdfCanvas
    
    

Tue Aug 25 13:50:29 2015

    Move core.geom.Rectangle to basic.geom.
    
    

Mon Aug 17 11:17:14 2015

    Make inline image exceptions more verbose. Unignore a sample from the book
    
    

Fri Aug 14 14:51:09 2015

    Create PdfImageXObject with awt image
    
    Implemented creating iText basics Image from java.awt.Image

DEVSIX-211


Thu Aug 13 18:21:47 2015

    Rename some colors in accordance with Java convention. Support headers and footers for tables (including large tables)
    
    

Mon Aug 10 16:19:00 2015

    Layout: Fix tables layout when cells have big rowspan
    
    The cells were not drawn in the first available area before.

DEVSIX-225


Tue Aug 4 16:25:09 2015

    Add code inspection tools to pom files
    
    Maven plugins during build and reporting phase:
* Checkstyle
* PMD
* Findbugs (with fb-contrib)

Maven plugins during reporting phase only:
* Javadoc
* Doxygen

Resolves: DEVSIX-154


Mon Aug 3 16:58:56 2015

    Remove duplicate information from POM files
    
    

Mon Aug 3 16:58:56 2015

    Change project names and urls
    
    

Fri Jul 24 16:02:42 2015

    Ignore config files related to code checking tools
    
    See: DEVSIX-154


Mon Aug 10 09:33:07 2015

    Fixed bug with creating JP2 Image
    
    There was wrong PdfImageXObject creation if image has JPXDecode or CCITTFaxDecode filter.

DEVSIX-217


Thu Aug 6 13:00:43 2015

    PdfAcroForm refactoring
    
    Replaced HashMap with LinkedHashMap. Fixed minor bug with appearance streams for radio button form fields.


Wed Aug 5 14:11:20 2015

    PdfAcroForm: Cache fields
    
    Added cache field, replace ArrayList of PDfFormFields with HashMap<String, PdfFormField>. Added removeField(String fieldName) method.

DEVSIX-224


Tue Aug 4 16:48:30 2015

    Image cache Moved image logic to basics module. ImageProcessing was refactored a lot. DEVSIX-149
    
    

Mon Aug 3 13:49:49 2015

    Minor layout fixes
    
    

Fri Jul 31 18:27:36 2015

    refactoring of rotation: removed rotation alignment property, moved rotation logic to block renderer; add vertical alignment support for block element
    
    

Fri Jul 31 15:19:46 2015

    Layout: fix table row groups calculation
    
    DEVSIX-175


Thu Jul 30 12:59:36 2015

    Rename colors in accordance with Java code convention
    
    

Wed Jul 29 18:01:33 2015

    Layout: optimizing default font creation
    
    

Wed Jul 29 17:08:08 2015

    First steps into LargeElement support. Speeding up and memory optimization
    
    

Tue Jul 28 14:43:20 2015

    Implemented form flattening. Added flatFields() method to PdfAcroForm class, which deletes interactive forms and replaces them with appearance stream (generates one if needed). Removed PdfTokenizer form core module, left one in basics. DEVSIX-210
    
    

Mon Jul 27 14:52:26 2015

    Layout: fix relayouting of paragraph. Rotation fixes. Empty list item fixes. Memory optimization of text rendering
    
    DEVSIX-201


Mon Jul 27 11:04:44 2015

    Merge remote-tracking branch 'origin/develop' into develop
    
    Conflicts:
	core/src/main/java/com/itextpdf/core/pdf/PdfObjectWrapper.java
	forms/src/main/java/com/itextpdf/forms/PdfAcroForm.java


Sun Jul 26 18:58:31 2015

    Added main interfaces and classes for digital signatures. Implemented basic functionality. Refactored some code.
    
    

Fri Jul 24 12:34:55 2015

    fix occupied area calculations; some refactoring
    
    

Fri Jul 24 11:31:40 2015

    Add kerning support at canvas and layout level. Cosmetic fixes
    
    DEVSIX-212


Thu Jul 23 14:17:55 2015

    add MustBeIndirect flag to PdfObject; removed document from PdfStream and most object wrappers constructors
    
    From now on pdf streams are direct on creation and are transformed into indirects either by explicit call of makeIndirect() method or when the stream is flushed. Also, most of the object wrappers are now direct on creation via any constructor, and they are supposed to be transformed into indirect objects only explicitly.

DEVSIX-207


Wed Jul 22 11:12:33 2015

    Add BarcodePostnet support
    
    (DEVSIX-193)


Tue Jul 21 15:47:54 2015

    Add BarcodeCodabar support
    
    (DEVSIX-194)


Tue Jul 21 14:31:07 2015

    Add BarcodeEANSUPP support
    
    (DEVSIX-195)


Tue Jul 21 11:12:18 2015

    Updated ImageTypes sample
    
    

Fri Jul 17 14:11:40 2015

    updated createFormXObject operation in 2D Barcodes.
    
    

Fri Jul 17 12:57:58 2015

    Move examples from the book into corresponding packages
    
    

Fri Jul 17 12:01:28 2015

    Add BarcodeDatamatrix support
    
    (DEVSIX-183)


Fri Jul 17 10:05:01 2015

    Merge branch 'develop' of gitlab.itextsupport.com:itext6/itextpdf into develop
    
    Conflicts:
	samples/src/test/resources/cmp_Listing_10_10_Barcodes.pdf


Fri Jul 17 10:02:39 2015

    Fix Barcode 1D XObject resources generation. Fix ImageRenderer rotation/scaling for both ImageXObjects and FormXObjects.
    
    

Thu Jul 16 16:00:20 2015

    update barcode sample
    
    

Thu Jul 16 15:57:16 2015

    add QRCode support
    
    DEVSIX-184


Thu Jul 16 15:20:54 2015

    added BarcodePDF417 support.
    
    DEVSIX-185


Wed Jul 15 15:09:26 2015

    Fix rotationAngle setter call in an example
    
    

Wed Jul 15 13:56:25 2015

    Port new samples from part 3 of the book and fix related bugs
    
    Use FontFacroty in default document font creation.
Fix ImageRenderer relaoyut bugs (width and height was stored in the state and in case of scaling and multiple relayout image was getting smaller and smaller).
Get rid of IMAGE_PARTIAL layout result.
Fix relayout bug of LineRenderer in case of justified layout.

DEVSIX-157


Tue Jul 14 16:21:09 2015

    Add BarcodeInter25 support
    
    (DEVSIX-192)


Mon Jul 13 17:49:12 2015

    different border styles and separate borders support
    
    DEVSIX-153


Mon Jul 13 14:20:20 2015

    added Barcode39 support. (DEVSIX-180)
    
    

Mon Jul 13 14:10:37 2015

    added Barcode39 support. (DEVSIX-180)
    
    

Fri Jul 10 12:47:37 2015

    added BarcodeEAN support (DEVSIX-182).
    
    

Thu Jul 9 09:46:05 2015

    fix cmp files
    
    

Wed Jul 8 16:40:35 2015

    Implemented form flattening. (DEVSIX-162)
    
    

Mon Jun 29 10:09:04 2015

    Added 1D Barcode support. Added Barcode128 implementation. (DEVSIX-178 & DEVSIX-181)
    
    

Wed Jun 24 13:50:51 2015

    Fixed bug with layout image. Added autoScale property to Image model object (DEVSIX-173).
    
    

Mon Jun 22 14:37:05 2015

    Add new properties for PdfFormField. Port a sample from book
    
    DEVSIX-163


Tue Jun 16 19:58:02 2015

    Add spacingRatio property (former spaceCharRatio in iText 5). Now this parameter lies in [0;1], not in [0; Inf] as before, and expresses the ratio of the free space which will be used for word spacing, 1-this parameter is the ratio for character spacing). Ported a couple of Bruno's samples for SO answers.
    
    

Fri Jun 12 17:38:03 2015

    Layout: new, enhanced strategy of rendering lists. Support for lists alignment (bullets should be kept intact as in html). With the new logic even nested div items are supported (at least simple cases).
    
    

Fri Jun 12 13:32:43 2015

    Alignments for paragraph DEVSIX-152
    
    

Thu Jun 11 09:25:47 2015

    Split alignment property into vertical and horizontal ones. JustifiedAll alignment for text. DEVSIX-152
    
    

Wed Jun 10 14:56:06 2015

    Use version of hsqldb which allows to run multiple database connections in parallel (for samples from the book).
    
    

Wed Jun 10 14:31:19 2015

    Layout: trim extra whitespace at the end of text lines.
    
    

Wed Jun 10 12:34:33 2015

    Support for character and word spacing in text. Initial work with justified alignment. DEVSIX-152
    
    

Mon Jun 8 13:59:48 2015

    Fixed some bugs with gif images and keepTogether property
    
    

Mon Jun 8 12:05:47 2015

    Cmp file for sample.
    
    

Mon Jun 8 12:00:19 2015

    Tests for bugs in keepTogether.
    
    

Mon Jun 8 09:33:25 2015

    Rename PdfRuntimeException to PdfException.
    
    

Fri Jun 5 16:35:06 2015

    Remove PdfRuntimeException from tests.
    
    

Thu Jun 4 12:58:34 2015

    Get rid of checked exceptions. Use PdfRuntimeException instead.
    
    

Wed Jun 3 10:06:39 2015

    Merge branch 'feature/layout_positioning' into develop
    
    Conflicts:
	basics/src/main/java/com/itextpdf/basics/image/ImageFactory.java
	canvas/src/main/java/com/itextpdf/canvas/color/DeviceGray.java
	canvas/src/main/java/com/itextpdf/canvas/image/MetaDo.java
	canvas/src/main/java/com/itextpdf/canvas/image/MetaFont.java


Wed Jun 3 09:49:46 2015

    Fixed positioning for block elements. Text layout improvements in cases of overflowing.
    
    

Tue Jun 2 14:27:56 2015

    Ported new examples from iText in Action book.
    
    

Fri May 29 10:24:03 2015

    Layout: initial support of fixed positioning.
    
    

Fri May 22 13:21:20 2015

    The bug with black transparent background was fixed (DEVSIX-143).
    
    

Wed May 20 14:58:14 2015

    Merge remote-tracking branch 'origin/develop' into develop
    
    Conflicts:
	core/src/main/java/com/itextpdf/core/pdf/PdfDocument.java
	core/src/main/java/com/itextpdf/core/pdf/PdfReader.java
	core/src/main/java/com/itextpdf/core/pdf/PdfString.java
	core/src/test/java/com/itextpdf/core/pdf/PdfReaderTest.java
	samples/src/test/java/com/itextpdf/samples/sandbox/objects/ListWithImageAsBullet.java
	utils/src/main/java/com/itextpdf/utils/PdfSplitter.java
	utils/src/test/java/com/itextpdf/utils/PdfSplitterTest.java


Wed May 20 14:20:21 2015

    Layout: Image support (DEVSIX-140)
    
    

Wed May 20 13:30:14 2015

    Normalize all the line endings. Don't forget the -w parameter for git-diff and git-blame.
    
    

Wed May 20 13:30:14 2015

    Normalize all the line endings. Don't forget the -w parameter for git-diff and git-blame.
    
    

Mon May 11 13:55:36 2015

    Added all samples to Maven tests.
    
    

Mon May 11 13:48:20 2015

    Merge branch 'develop' of gitlab.itextsupport.com:itext6/itextpdf into develop
    
    Conflicts:
	samples/src/test/resources/cmp_Listing_07_02_LinkActions.pdf


Mon May 11 13:34:14 2015

    Layout: lists support. Samples tests refactoring. List numbering classes(could be reused in page labels). Margins support. Better leading support.
    
    

Fri May 8 11:37:34 2015

    Fixed the sample with links
    
    

Thu May 7 14:48:53 2015

    Layout: links implementation
    
    

Wed Apr 29 14:04:35 2015

    Unignore TilingPatternColor sample test after fix of png image processing.
    
    

Wed Apr 29 10:19:22 2015

    DEVSIX-129 Text layout: support leading.
    
    

Tue Apr 28 14:18:16 2015

    DEVSIX-129 Layout: using ascender and descender when writing text. Removed borders used for testing purposes from samples.
    
    

Mon Apr 27 08:42:03 2015

    DEVSIX-129 Layout: writing text line by line (LineRenderer). Unignore a couple of tests.
    
    

Fri Apr 24 14:54:06 2015

    DEVSIX-129 Initial work for text writing. Element structure. Repalced iText5's CompareTool usage with iText6 one. New layout tests.
    
    

Wed Apr 22 14:37:51 2015

    ProcSet resource support implemented. (DEVSIX-132)
    
    

Wed Apr 15 12:59:30 2015

    Merge branch 'feature/layout_ptototyping' into develop
    
    Conflicts:
	pom.xml


Wed Apr 15 12:53:57 2015

    Layout: relayout possibility.
    
    

Wed Apr 15 10:22:28 2015

    Sample tests now using itext6 CompareTool. Also made a little fix in CompareTool.
    
    

Tue Apr 14 18:40:42 2015

    New samples. Added new cmp files for old samples. Fixed multiarea layout.
    
    

Tue Apr 14 17:09:47 2015

    Merge branch 'develop' into feature/layout_ptototyping
    
    Conflicts:
	samples/src/main/java/com/itextpdf/samples/Listing_03_01_FestivalOpening.java
	samples/src/main/java/com/itextpdf/samples/Listing_99_02_ComplexLayout_Option1.java
	samples/src/main/java/com/itextpdf/samples/Listing_99_02_ComplexLayout_Option2.java


Tue Apr 14 16:54:16 2015

    Layout: support for an element to have multiple areas to render to.
    
    

Tue Apr 14 14:54:18 2015

    Added CompareTool class.
    
    

Mon Apr 13 16:46:52 2015

    Created tests from samples. Also fixed a small bug with getLastPage method in PdfDocument and LastPage constant in it
    
    

Mon Apr 13 09:44:19 2015

    ComplexDocumentLayout  example now works correctly. Fix of fontList in PdfDocument. Added cmp files to layout tests.
    
    

Fri Apr 10 12:58:26 2015

    Layout: initial work with overflow.
    
    

Thu Apr 9 11:08:33 2015

    Initial commit of layout prototyping. Some samples have been changed a bit.
    
    

Thu Apr 9 15:55:00 2015

    Rename package itextpdf.core.fonts to itextpdf.core.font.
    
    

Thu Apr 9 10:54:14 2015

    Add Type1Font and PdfType1Font. Add metrics for standard PDF fonts. Add predefined CMaps. PdfCopy tests refactoring.
    
    

Mon Apr 6 09:49:12 2015

    Test fixes and minor changes.
    
    

Wed Apr 1 14:02:50 2015

    DEVSIX-121 Utils: Split document by page numbers
    
    

Mon Mar 23 09:57:53 2015

    Avoid pattern color space object multiplication.
    
    

Mon Mar 2 10:40:09 2015

    PdfActions have been added.
    
    

Wed Feb 25 12:20:31 2015

    Pattern colors implementation.
    
    

Fri Feb 20 15:08:36 2015

    Added some PdfAction types
    
    

Thu Feb 19 15:26:03 2015

    Big refactoring of images. Add support of Png, Jbig2, Gif images. Wmf images not finished, a lot of dependencies from fonts.
    
    

Thu Feb 5 14:00:30 2015

    Optional content implementation.
    
    

Sat Jan 10 11:01:32 2015

    1. Added partial DeviceN/NChannel implementation 2. Refactored package names and tests
    
    

Tue Nov 25 16:27:41 2014

    Added PdfCanvas.release() method
    
    

Fri Nov 21 16:56:20 2014

    Added Form XObject handling
    
    

Mon Nov 10 17:16:15 2014

    PdfReader: support documents with incremental updates. PdfReader: add rebuildXref field. PdfReader tests with append mode. PdfArray: get(index) return refersTo().
    
    

Thu Oct 23 16:38:47 2014

    Add support of PdfStream: getStreamBytes() method. Add support of XRefStream and ObjectStream. PdfDictionary, PdfArray: add getAs methods. PdfReaderTest: add test with FullCompression.
    
    

Tue Oct 21 15:18:59 2014

    'io' renamed to 'basics'
    
    

Mon Oct 20 15:30:52 2014

    Added basic Image class and basic Exception class
    
    

Thu Oct 16 11:45:14 2014

    Pre-Tested Commit
    
    

Tue Oct 14 12:23:17 2014

    Add PdfXRefTable as separate structure.
    
    

Wed Oct 8 16:39:05 2014

    1. Added copy page test. 2. Project structure refactoring.
    
    

Tue Oct 7 12:41:09 2014

    IOException has been replaced by PdfException in some methods
    
    

Tue Sep 30 16:48:05 2014

    Refactoring. PdfPage and neighbours moved to PdfObjectWrapper level.
    
    

Tue Sep 30 13:48:12 2014

    PdfContentStream has been removed
    
    

Wed Aug 20 12:11:38 2014

    Add canvas text methods. Objects flushing bug fix. Add PdfStandardFont.
    
    

Thu Jul 24 13:42:44 2014

    
    
    

Tue Jul 22 14:28:23 2014

    1. Different way of working with content streams 2. Fixed compilation errors
    
    

Fri Jul 18 11:59:55 2014

    Added first implementation of PdfWriter
    
    

Tue Jul 1 12:30:00 2014

    Added comments, refactored some classes
    
    

Mon Jun 30 14:24:57 2014

    
    
    

Fri Jun 27 16:17:38 2014

    1. New sample has been added 2. Some old samples have been changed 3. Some changes in API
    
    

Tue Apr 22 16:04:50 2014

    Added event handling. Currently PdfDocument is the only event dispatcher. Later we will add more if needed.
    
    

Fri Apr 18 17:19:12 2014

    
    
    

Fri Apr 18 16:50:44 2014

    
    
    

Thu Apr 17 10:42:22 2014

    iText6 first commit
    
    
