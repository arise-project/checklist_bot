  square of
          opposition
John MacFarlane
Home
Papers
Drafts
Books
Talks
Courses
Students
CV
Misc
Tools
Beyond Markdown
This essay was originally posted at https://talk.commonmark.org/t/beyond-markdown/2787 in April, 2018.

In developing Commonmark, we have tried, as far as possible, to remain faithful to John Gruber’s original Markdown syntax description. We have diverged from it only occasionally, in the interest of removing ambiguity and increasing uniformity, and with the addition of a few syntax elements that are now virtuously ubiquitous (like fenced code blocks and shortcut reference links).

There are very good reasons for being conservative in this way. But this respect for the past has made the CommonMark spec a very complicated beast. There are 17 principles governing emphasis, for example, and these rules still leave cases undecided. The rules for list items and HTML blocks are also very complex. All of these rules lead to unexpected results sometimes, and they make writing a parser for CommonMark a complex affair. I despair, at times, of getting to a spec that is worth calling 1.0.

What if we weren’t chained to the past? What if we tried to create a light markup syntax that keeps what is good about Markdown, while revising some of the features that have led to bloat and complexity in the CommonMark spec?

Let me be clear up front that I’m not suggesting any change in the goals of the Commonmark project. If these reflections lead to anything, it should probably be an entirely new project under a new name. And, being realistic, the burdens of maintaining backwards compatibility are light in comparison with the enormous practical costs of moving existing systems to a new light markup language. Still…I think it can be useful to daydream.

In what follows, I’ll go through the six features of Markdown that I think have created the most difficulties, and I’ll suggest how each pain point can be fixed.

1. Emphasis
In Markdown, emphasis is created by surrounding text with * or _ characters, *like this*. Strong emphasis is created by doubling these, **like this**. That all sounds very simple, and it’s visually clear which one is strong emphasis.

Unfortunately, these simple statements aren’t enough to pin down the syntax. Consider, for example,

**this* text**
Our simple rules are consistent with both of these readings:

<strong>this* text</strong>
<em><em>this</em> text</em>*
So, to fully specify emphasis parsing, we need additional rules. The 17 discouragingly complex rules in the CommonMark spec are intended to force the sorts of readings that humans will find most natural.

It seems to me that the use of doubled characters for strong emphasis, and the possibility of emphasizing even part of a word, as in fan*tas*tic, have made the problem of specifying emphasis parsing far worse, by vastly increasing the ambiguities the spec must resolve. Depending on context, a string of three *** in the middle of a word might be any of the following:

A * character followed by the beginning of strong emphasis.
The end of strong emphasis followed by a * character.
The end of normal emphasis, a * character, then the beginning of normal emphasis.
The end of strong emphasis followed by the beginning of normal emphasis.
The end of normal emphasis followed by the beginning of strong emphasis.
The end of normal emphasis followed by a literal **.
A literal ** followed by the beginning of normal emphasis.
Literal ***.
How to fix emphasis
To dramatically reduce ambiguities, we can remove the doubled character delimiters for strong emphasis. Instead, use a single _ for regular emphasis, and a single * for strong emphasis. Emphasis would now start with a left-flanking but not right-flanking delimiter and end with a right-flanking but not left-flanking delimiter of the same kind.

For intraword emphasis, we’d require a special syntax:

fan~_tas_~tic
Intraword emphasis is extremely rare, so it’s a good tradeoff to make it a little harder, in exchange for simplifying the rules (and conceptual model) for emphasis in general. The special character ~ here acts like a space for purposes of parsing emphasis (allowing the intraword _ to start and end emphasis), but isn’t rendered as a space. (It thus behaves like an escaped space does in reStructuredText.)

Added 2022-04-22: This would also give us a way to express something that is currently inexpressible in commonmark: a<em>[b]</em>c. There is currently no way to write this with commonmark’s rules, since in a*[b]*c the first * is right-flanking and the second * is left-flanking. But with the proposed syntax, it could be written as a~_[b]_~c.

2. Reference links
The usual treatment of reference links makes it impossible to classify any syntax element until the whole document has been parsed. For example, consider

[foo][bar][baz]

[bar]: url
This is interpreted as

<p><a href="url">foo</a>[baz]</p>
But suppose we define a link for baz instead of bar:

[foo][bar][baz]

[baz]: url
Then we get:

<p>[foo]<a href="url">bar</a></p>
So, we can’t tell whether [foo] is literal bracketed text or a link with link description foo until we’ve parsed the entire document.

This makes syntax highlighting very difficult, and it also complicates writing parsers. For example, you can’t parse links, then resolve references in the AST after the document is parsed.

How to fix reference links
Make reference links recognizable by their shape alone, independent of what references are defined in the document. Thus,

[foo][bar][baz]
would be parsed as a link with link text foo to whatever URL is defined for reference bar (or to nothing, if none is defined), followed by literal text [baz].

Shortcut references like

[foo]

[foo]: url
would have to be disallowed (unless we were willing to force writers to escape all literal bracket characters). The compact form could be used instead:

[foo][]

[foo]: url
This is a bit more typing, but it makes it clear and unambiguous that there is a link.

3. Indented code blocks and lists
Parsing indented code blocks is straightforward, but their presence complicates the rules for list items.

In specifying the syntax for list items, we need to say how far content must be indented in order to be considered part of the list item. The original Markdown syntax documents hinted at a “four-space rule,” requiring four spaces indentation, but implementations rarely followed that, and most people find it counterintuitive that

- a
  - b
wouldn’t be considered a nested list. So, in CommonMark, we surveyed a large number of possible rules, eventually ending up with a rule requiring the contents of the list item to be indented at least to the level of the first non-space content after the list marker:

  -  Item

     ^-- contents must be indented to here.
This is not a bad rule, but it adds complexity: one has to keep track not just of the position of the list marker, but of the position of the first non-space content that follows it. And then one needs special rules for cases like empty list items and list items that begin with indented code. Finally, many people still find it surprising that, for example, this isn’t a nested list:

- a
 - b
Thus one might ask: why not just require that the contents of a list item be indented at least one space past the list marker? That’s the obvious minimal rule. What blocks this is the presence of indented code blocks. If block-level content under a list item begins at one space indent after the list marker, then indented code would have to be indented five spaces past the list marker. Not only is that incompatible with the eight spaces indicated in the original Markdown syntax description, it leads to terrible results with longer list markers:

99.  Here's my list item.

     And this is indented code! Even though it
     lines up with the paragraph above!
To sum up: most of the complexity in the rules for list items is motivated by the need to deal with indented code blocks.

How to fix indented code blocks and lists
Fenced code blocks are now usually preferred to indented code blocks, because you can specify a syntax for highlighting and you needn’t indent/deindent when copying and pasting code. Since we have fenced code blocks, we don’t need indented code blocks. So, we can just get rid of them.

This frees up indentation to be used more flexibly to indicate list nesting, and we can embrace the simple, obvious rule that the contents of a list item must be indented at least one space relative to the list marker.

Another advantage of removing indented code blocks is that initial indentation can now be ignored in general, except insofar as it affects lists.

4. Raw HTML
From the beginning, you could insert raw HTML into Markdown documents, and it would be passed through verbatim. The idea is that you could drop back to raw HTML for anything that can’t be expressed in plain text.

This sounds simpler than it is. From the beginning, Markdown.pl distinguished between inline and block level HTML. Inline HTML tags were passed through verbatim, but their contents could be interpreted as Markdown:

<em>**hi**</em>
would give you

<em><strong>hi</strong></em>
Block-level HTML content, it was stipulated, should be separated by blank lines, and the start and end tags should not be indented. In such HTML blocks, everything would be passed through verbatim, and not interpreted as Markdown. So,

<div>
*hello*
</div>
would just give you

<div>
*hello*
</div>
This raised several problems. First, how do we identify block-level content? Do we need to hard-code a list of HTML elements that may change as HTML evolves? What about elements like <del> that can occur in inline or block contexts?

Second, what about block-level HTML that is not properly separated and indented?

hi <div>
  hello</div>
Should parsers just treat it as inline HTML and generate invalid HTML?

Third, how do we identify the end of an HTML block? Given that tags can be nested, this requires nontrivial HTML parsing. The released version of Markdown.pl produced invalid HTML for a doubly-nested <div> element; a beta version designed to fix this problem had serious performance issues.

CommonMark’s spec for HTML blocks was designed to make it easy to parse raw HTML blocks (without indefinite lookahead or full implementation of HTML parsing), and also to make it possible for authors to include CommonMark content inside block-level HTML tags, if they wanted to. But the result is rather complex: seven distinct pairs of start and end conditions. The rules for inline HTML are also complex, with a large number of definitions.

In addition, as Markdown has become useful not just for creating HTML, but for creating documents in a number of different formats, the way HTML is singled out for raw pass-through has come to seem a bit arbitrary. Those who author in other formats would benefit from a way to pass through raw content, too.

How to fix raw HTML
Instead of passing through raw HTML, we should introduce a special syntax that allows passing through raw content of any format. For this we can overload our existing containers for raw strings: code spans and code blocks:

This is raw HTML: `<img src="myimage.jpg">`{=html}.

And here's an HTML block:

```{=html}
<div id="main">
 <div class="article">
```
But we can do LaTeX too:

```{=latex}
\begin{tikzpicture}
\node[inner sep=0pt] (russell) at (0,0)
    {\includegraphics[width=.25\textwidth]{bertrand_russell.jpg}};
\node[inner sep=0pt] (whitehead) at (5,-6)
    {\includegraphics[width=.25\textwidth]{alfred_north_whitehead.jpg}};
\draw[<->,thick] (russell.south east) -- (whitehead.north west)
    node[midway,fill=white] {Principia Mathematica};
\end{tikzpicture}
```
We could even pass through different raw content to different formats, for example including HTML and LaTeX versions of a complex figure.

5. Lists and blank lines
Can a list interrupt a paragraph, like this?

Paragraph test.
- Item one
- Item two
The original Markdown syntax documentation does not settle this, but Markdown.pl and its test suite require a blank line between paragraph text and a following list. As the test suite indicates, this requirement was introduced in order to avoid accidental creation of lists by things like:

I think he weighed 200 pounds, maybe even
220.  But he was no more than five feet tall.
However, one exception was made: when the paragraph text is itself part of a list item, no blank line is required.

-   Paragraph one

    paragraph two
    - sublist item one
    - sublist item two
If this exception were not made, then we would not be able to recognize a nested list in this kind of case:

- a
  - b
  - c
- d
In thinking about the CommonMark spec for list items, we realized that the Markdown.pl behavior violates what we called the principle of uniformity, which says that the contents of a list item should have the same meaning they would have outside of the list item. This principle implies that if

a
- b
- c
does not contain a list, then

- a
  - b
  - c
- d
does not contain a sublist. We think that the principle of uniformity is important. Indeed, the way we specify list items and block quotes presupposes it. This means that we faced a choice: either require a blank line between paragraph text and a following list, or allow lists to interrupt paragraphs, and risk accidental interpretation of paragraph text as a list. We took the first option to be off the table, since it is very common in Markdown to have tight sublists without a preceding blank line. So we opted for the second option, mitigating the damage with an ugly heuristic (we only allow an ordered list to interrupt a paragraph when the list number is 1).

How to fix lists and blank lines
We should require a blank line between paragraph text and a list. Always. That means, even in sublists. So, to create a tight list with a sublist, you’d write:

- a

  - b
  - c

- d
We’ll say a list is tight if it contains at least one pair of items with no blank line between, so in the above example, the inner list is tight and the outer list is not. To get both lists tight:

- a

  - b
  - c
- d
6. Attributes
Markdown offers no general way to add attributes (such as classes or identifiers) to elements. This deprives it of a native way of creating internal links to sections of a document. (Many implementations have introduced subtly different ways of automatically generating identifiers from headers.) It also deprives it of a natural extension mechanism. Markdown has containers for inlines (e.g. emphasis), blocks (e.g. block quote), and raw inline content (code spans), and raw block content (code blocks). If arbitrary attributes could be attached to these, they could be manipulated by filters to produce very flexible output. For example, one could treat a block quote with the class “warning” as a warning admonition, or one could treat a code block with the class “dot” as a graphviz dot diagram, to be rendered as an image. Currently, though, the only way to attach attributes to an element is to drop down to raw HTML.

How to fix attributes
Introduce a syntax for an attribute specification. Following pandoc, use braces {} for this. An identifier is indicated with #. A bare word is treated as a class (or, an initial . could be required as in pandoc). Use = for an arbitrary key/value attribute.

Allow attributes to be added on the line before any block element and directly after any inline element:

{#myheader}
# The *Blue Title*{blue position=left}
Here the identifier myheader is added to the header block, and the class blue and key/value attribute position=left are added to the emphasized text Blue Title.

Attributes specifiers must fit on one line, but several may be used (and will then be combined):

{#mywarning}
{warning}
> Don't try this at home!
> It might be dangerous.
Perhaps it would be helpful to add a syntax for unadorned inline spans, and a fenced generic block container, as in pandoc. But we can use emphasis for an inline container and block quote for a block container, so this wouldn’t be absolutely necessary.

Summary of recommendations
Emphasis

Use distinct characters for emphasis and strong emphasis.
Don’t use doubled-character delimiters.
Simplify emphasis rules.
Introduce special syntax for intraword emphasis, with ~ behaving like a space as far as parsing emphasis goes, but render as nothing.
Reference links

Don’t make parsing something as a link depend on whether a reference link definition exists elsewhere in the document.
Remove shortcut reference links.
Code

Remove indented code blocks. Use only fenced blocks for code.
Lists

Use a simple rule for determining what belongs under a list item: anything indented at all with respect to the list marker belongs in the item.
Require a blank line between paragraph content and a following list.
Revise rules for tight lists: a list is tight if any two items lack a blank line between them.
HTML

Remove automatic pass-through of raw HTML. Things like <br> will now be treated as regular text and escaped.
Introduce an explicit syntax for passing through raw content in an arbitrary format. In inline contexts, a code span followed by {=FORMAT}; in block contexts, a fenced code block with info string {=FORMAT}.
Attributes

Introduce a uniform attribute syntax, like this: {class #identifier key=value}.
Allow attributes on any block element. The identifier must appear by itself on the line before the block element. Multiple attributes can be specified on successive lines; they will be combined.
Allow attributes on any inline element. The identifier must appear immediately after (and adjacent to) the inline element to which it is to apply.
© 2006–2022 John MacFarlane — powered by yst
