# Invoice Generator

## Refactorings in this version

- Extracting `addLineForSingleItem()` again after preparatory refactoring
  - Extracting `for`-loop body as `addLineForSingleItem()`
  - Change Signature for `addLineForSingleItem()` to reorder arguments
  - Extract function `printVolumeDiscountAndTotal()`
