<template lang="html">
  <textarea id="sample">Hi</textarea>
</template>

<script>
import 'suneditor/dist/css/suneditor.min.css'
import suneditor from 'suneditor'
import plugins from 'suneditor/src/plugins'

export default {
  mounted() {
    suneditor.create('sample', {
        width: '80%',
        height: '500px',
        plugins: plugins,
        buttonList: [
            ['undo', 'redo'],
            ['font', 'fontSize', 'formatBlock'],
            ['paragraphStyle', 'blockquote'],
            ['bold', 'underline', 'italic', 'strike', 'subscript', 'superscript'],
            ['fontColor', 'hiliteColor', 'textStyle'],
            ['removeFormat'],
            '/', // Line break
            ['outdent', 'indent'],
            ['align', 'horizontalRule', 'list', 'lineHeight'],
            ['table', 'link', 'image', 'video', 'audio' /** ,'math' */], // You must add the 'katex' library at options to use the 'math' plugin.
            /** ['imageGallery'] */ // You must add the "imageGalleryUrl".
            ['fullScreen', 'showBlocks', 'codeView'],
            ['preview', 'print'],
            ['save', 'template']
        ]
    });

    // You can also load what you want
    suneditor.create('sample', {
        plugins: [plugins.font],
        // Plugins can be used directly in the button list
        buttonList: [
            ['font', plugins.image]
        ]
    });
  }
}
</script>

<style lang="css">
</style>