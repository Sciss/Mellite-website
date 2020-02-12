# Tips and Recipes for FScape

## All-pairs sequences

The task is to generate the sequence of all possible (undirected) pairs from a given sequence, for example to match
pairs of slices using the `Slices` UGen. In a plain Scala collection, if the input sequence were the numbers
from zero until four, this would be

@@snip [Scala pairwise combinations]($sp_tut$/FScapeRecipes.scala) { #fsc-recipe1-plain }

The resulting pairs being `Seq((0,1), (0,2), (0,3), (1,2), (1,3), (2,3))`. In FScape, we can emulate the looping
over the input sequence using `RepeatWindow`, and the guard filter by using the `FilterSeq` UGen:

@@snip [FScape pairwise combinations]($sp_tut$/FScapeRecipes.scala) { #fsc-recipe1 }

The expected number of pairs is `size * (size - 1) / 2`, so six. If we group the values for `A` and `B`, we
get the expected sequence `(0,1), (0,2), (0,3), (1,2), (1,3), (2,3)`.
