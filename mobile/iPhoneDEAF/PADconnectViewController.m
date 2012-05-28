//
//  PADconnectViewController.m
//  iPhoneDEAF
//
//  Created by developer on 12. 1. 10..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import "PADconnectViewController.h"
#import "PADtextViewController.h"

@implementation PADconnectViewController
@synthesize padIpAddress;
@synthesize padPortNumber;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
}
*/

- (void)viewDidUnload
{
    [self setPadIpAddress:nil];
    [self setPadPortNumber:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"MySegue"]) {
        
        
        PADtextViewController *vc = segue.destinationViewController;
        
        vc.padipsend = padIpAddress.text;
        vc.padportsend = padPortNumber.text;
        
        
        
    }
}



- (void)dealloc {
    [padIpAddress release];
    [padPortNumber release];
    [super dealloc];
}
@end
