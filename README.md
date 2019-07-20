# ImageLoadLibrary
Loads the image onto an Image View inside a Recycler View effectively.

*Loading Images:*

Pass the image view, url and the adapter position to the library.

```kotlin
imageLibrary.loadImage(imageView, url, position)
```

If the image is present in the cache, library load the image from the cache, otherwise it make a network call to download the image.
