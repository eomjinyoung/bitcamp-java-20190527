#include <stdio.h>
#include <stdlib.h>

int main() {
  char *name;
  //name = (char*) malloc(100);
  printf("name? ");
  scanf("%s", name);
  printf("Hello! %s!\n", name);
  return 0;
}