# ImageLoadLibrary
Loads the image onto an Image View inside a Recycler View effectively.

**Loading Images:**

Pass the image view, url and the adapter position to the library.

```kotlin
imageLibrary.loadImage(imageView, url, position)
```

If the image is present in the cache, library load the image from the cache, otherwise it make a network call to download the image.

**Cancel Loading Images:**

If the recycler view is scrolled fast, there is a probability that the api calls finishes after the view is recycled. In this case, the user will see wrong image at a wrong position.

The library cancels the live api calls to avoid updating the recycled views with wrong image.

To do so, add the `recycledView` inside the overridden function `onViewRecycled`.

```kotlin
imageLibrary.recycledView(holder.adapterPosition)
```
