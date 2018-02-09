# DMN and KIE

This repo attempts to illustrate the different ways of invoking Decision Model and Notation (DMN) that are wrapped in Knowledge is Everything (KiE) deployments.

## A Simple "Decision"

In order to focus on the interaction, the simplest of "decisions" was concocted.  This simple classification type decision takes a Age of movie patron, and determines an Age Classification code.   Here is the DRD:

![DRD Diagram](movieticket-age-drd.png "Age classification for moview ticket sales")

And here is the Decision Table logic inside the AgeClassification decision:

![Decision Table](age-classification-decision-table.png "Decision table mapping age ranges to age classifications")
